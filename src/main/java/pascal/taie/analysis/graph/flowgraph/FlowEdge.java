

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.util.graph.Edge;

/**
 * Represents edges in flow graph.
 */
public interface FlowEdge extends Edge<Node> {

    FlowKind kind();

    default String info() {
        return kind().name();
    }
}
