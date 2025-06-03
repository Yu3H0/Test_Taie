

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.graph.callgraph.OtherEdge;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;

/**
 * Represents call edge from invokedynamic to actual target method.
 */
class InvokeDynamicCallEdge extends OtherEdge<CSCallSite, CSMethod> {

    InvokeDynamicCallEdge(CSCallSite csCallSite, CSMethod callee) {
        super(csCallSite, callee);
    }
}
