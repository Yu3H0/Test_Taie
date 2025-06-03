

package pascal.taie.analysis.graph.flowgraph;

import pascal.taie.language.classes.JField;

public class StaticFieldNode extends Node {

    private final JField field;

    StaticFieldNode(JField field, int index) {
        super(index);
        this.field = field;
    }

    public JField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "StaticFieldNode{" + field + '}';
    }
}
