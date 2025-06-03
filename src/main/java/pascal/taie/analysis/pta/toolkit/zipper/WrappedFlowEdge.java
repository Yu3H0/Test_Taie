

package pascal.taie.analysis.pta.toolkit.zipper;

import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.FlowKind;
import pascal.taie.analysis.graph.flowgraph.Node;

record WrappedFlowEdge(Node source, Node target) implements FlowEdge {

    @Override
    public FlowKind kind() {
        return FlowKind.OTHER;
    }
}
