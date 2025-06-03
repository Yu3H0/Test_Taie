

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.language.classes.JField;

public class InstanceFieldNode extends InstanceNode {

    private final JField field;

    InstanceFieldNode(Obj base, JField field, int index) {
        super(base, index);
        this.field = field;
    }

    public JField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "InstanceFieldNode{" + base + "." + field.getName() + "}";
    }
}
