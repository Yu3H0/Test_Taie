

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.util.Indexable;

/**
 * Nodes in object flow graph.
 */
public abstract class Node implements Indexable {

    private final int index;

    Node(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
