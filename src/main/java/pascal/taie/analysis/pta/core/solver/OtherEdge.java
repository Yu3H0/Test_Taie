

package pascal.taie.analysis.pta.core.solver;

import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.pta.core.cs.element.Pointer;

/**
 * Base class for flow edges of {@link FlowKind#OTHER}.
 * Implementation of {@link FlowKind#OTHER} flow edges should inherit this class.
 */
public abstract class OtherEdge extends PointerFlowEdge {

    protected OtherEdge(Pointer source, Pointer target) {
        super(FlowKind.OTHER, source, target);
    }

    /**
     * @return String representation of information for this edge.
     * It contains simple name of the edge class to distinguish
     * from other classes of {@link FlowKind#OTHER} flow edges.
     */
    @Override
    public String getInfo() {
        return kind().name() + "." + getClass().getSimpleName();
    }
}
