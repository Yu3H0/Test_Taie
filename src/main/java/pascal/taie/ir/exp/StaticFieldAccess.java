

package pascal.taie.ir.exp;

import pascal.taie.ir.proginfo.FieldRef;

/**
 * Representation of static field access expression, e.g., T.f.
 */
public class StaticFieldAccess extends FieldAccess {

    public StaticFieldAccess(FieldRef fieldRef) {
        super(fieldRef);
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return fieldRef.toString();
    }
}
