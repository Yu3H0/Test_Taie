

package pascal.taie.ir.exp;

import pascal.taie.ir.proginfo.FieldRef;
import pascal.taie.language.type.Type;

/**
 * Representation of field access expressions.
 */
public abstract class FieldAccess implements LValue, RValue {

    protected final FieldRef fieldRef;

    protected FieldAccess(FieldRef fieldRef) {
        this.fieldRef = fieldRef;
    }

    public FieldRef getFieldRef() {
        return fieldRef;
    }

    @Override
    public Type getType() {
        return fieldRef.getType();
    }
}
