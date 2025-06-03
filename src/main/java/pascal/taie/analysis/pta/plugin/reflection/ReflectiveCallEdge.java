

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.analysis.graph.callgraph.OtherEdge;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.ir.exp.Var;

import javax.annotation.Nullable;

/**
 * Represents reflective call edges.
 */
class ReflectiveCallEdge extends OtherEdge<CSCallSite, CSMethod> {

    /**
     * Variable pointing to the array argument of reflective call,
     * which contains the arguments for the reflective target method, i.e.,
     * args for constructor.newInstance(args)/method.invoke(o, args).
     * This field is null for call edges from Class.newInstance().
     */
    @Nullable
    private final Var args;

    ReflectiveCallEdge(CSCallSite csCallSite, CSMethod callee, @Nullable Var args) {
        super(csCallSite, callee);
        this.args = args;
    }

    @Nullable
    Var getArgs() {
        return args;
    }
}
