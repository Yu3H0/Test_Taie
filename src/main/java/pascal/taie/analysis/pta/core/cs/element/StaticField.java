

package pascal.taie.analysis.pta.core.cs.element;

import pascal.taie.language.classes.JField;
import pascal.taie.language.type.Type;

/**
 * Represents static field pointers.
 */
public class StaticField extends AbstractPointer {

    private final JField field;

    StaticField(JField field, int index) {
        super(index);
        this.field = field;
    }

    /**
     * @return the corresponding static field of the StaticField pointer.
     */
    public JField getField() {
        return field;
    }

    @Override
    public Type getType() {
        return field.getType();
    }

    @Override
    public String toString() {
        return field.toString();
    }
}
