

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.analysis.pta.core.heap.Obj;

public class ArrayIndexNode extends InstanceNode {

    ArrayIndexNode(Obj base, int index) {
        super(base, index);
    }

    @Override
    public String toString() {
        return "ArrayIndexNode{" + base + "}";
    }
}
