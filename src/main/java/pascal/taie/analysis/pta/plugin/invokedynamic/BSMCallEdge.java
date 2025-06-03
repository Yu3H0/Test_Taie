

package pascal.taie.analysis.pta.plugin.invokedynamic;

import pascal.taie.analysis.graph.callgraph.OtherEdge;
import pascal.taie.analysis.pta.core.cs.element.CSCallSite;
import pascal.taie.analysis.pta.core.cs.element.CSMethod;

/**
 * Represents call graph edge from invokedynamic to bootstrap method.
 */
class BSMCallEdge extends OtherEdge<CSCallSite, CSMethod> {

    public BSMCallEdge(CSCallSite csCallSite, CSMethod callee) {
        super(csCallSite, callee);
    }
}
