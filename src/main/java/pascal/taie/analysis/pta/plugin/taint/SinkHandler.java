

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.graph.callgraph.CallKind;
import pascal.taie.analysis.graph.callgraph.Edge;
import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.analysis.pta.core.cs.element.ArrayIndex;
import pascal.taie.analysis.pta.core.cs.element.CSObj;
import pascal.taie.analysis.pta.core.cs.element.InstanceField;
import pascal.taie.analysis.pta.core.cs.element.Pointer;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.analysis.pta.plugin.util.InvokeUtils;
import pascal.taie.ir.exp.Var;
import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JMethod;
import pascal.taie.util.collection.MultiMap;
import pascal.taie.util.collection.MultiMapCollector;
import pascal.taie.util.collection.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles sinks in taint analysis.
 */
class SinkHandler extends Handler {

    private final List<Sink> sinks;

    SinkHandler(HandlerContext context) {
        super(context);
        sinks = context.config().sinks();
    }

    Set<TaintFlow> collectTaintFlows() {
        PointerAnalysisResult result = solver.getResult();
        Set<TaintFlow> taintFlows = Sets.newOrderedSet();
        for (Sink sink : sinks) {
            result.getCallGraph()
                    .edgesInTo(sink.method())
                    // TODO: handle other call edges
                    .filter(e -> e.getKind() != CallKind.OTHER)
                    .map(Edge::getCallSite)
                    .map(sinkCall -> collectTaintFlows(sinkCall, sink))
                    .forEach(taintFlows::addAll);
        }
        if (callSiteMode) {
            MultiMap<JMethod, Sink> sinkMap = sinks.stream()
                    .collect(MultiMapCollector.get(Sink::method, s -> s));
            // scan all reachable call sites to search sink calls
            result.getCallGraph()
                    .reachableMethods()
                    .flatMap(m -> m.getIR().invokes(false))
                    .forEach(callSite -> {
                        JMethod callee = callSite.getMethodRef().resolveNullable();
                        if (callee != null) {
                            for (Sink sink : sinkMap.get(callee)) {
                                taintFlows.addAll(collectTaintFlows(callSite, sink));
                            }
                        }
                    });
        }
        return taintFlows;
    }

    private Set<TaintFlow> collectTaintFlows(
            Invoke sinkCall, Sink sink) {
        IndexRef indexRef = sink.indexRef();
        Var arg = InvokeUtils.getVar(sinkCall, indexRef.index());
        SinkPoint sinkPoint = new SinkPoint(sinkCall, indexRef);
        // obtain objects to check for different IndexRef.Kind
        Set<Obj> objs = switch (indexRef.kind()) {
            case VAR -> csManager.getCSVarsOf(arg)
                    .stream()
                    .flatMap(Pointer::objects)
                    .map(CSObj::getObject)
                    .collect(Collectors.toUnmodifiableSet());
            case ARRAY -> csManager.getCSVarsOf(arg)
                    .stream()
                    .flatMap(Pointer::objects)
                    .map(csManager::getArrayIndex)
                    .flatMap(ArrayIndex::objects)
                    .map(CSObj::getObject)
                    .collect(Collectors.toUnmodifiableSet());
            case FIELD -> csManager.getCSVarsOf(arg)
                    .stream()
                    .flatMap(Pointer::objects)
                    .map(o -> csManager.getInstanceField(o, indexRef.field()))
                    .flatMap(InstanceField::objects)
                    .map(CSObj::getObject)
                    .collect(Collectors.toUnmodifiableSet());
        };
        return objs.stream()
                .filter(manager::isTaint)
                .map(manager::getSourcePoint)
                .map(sourcePoint -> new TaintFlow(sourcePoint, sinkPoint))
                .filter(this::isApplicationTaint)
                .collect(Collectors.toSet());
    }

    private boolean isApplicationTaint(TaintFlow taintFlow) {
        return taintFlow.sourcePoint().getContainer().isApplication()
                && taintFlow.sinkPoint().sinkCall().getContainer().isApplication();
    }
}
