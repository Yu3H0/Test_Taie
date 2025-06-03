

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.FieldAccess;
import pascal.taie.ir.exp.LValue;
import pascal.taie.ir.exp.RValue;
import pascal.taie.ir.proginfo.FieldRef;

/**
 * Load/Store field statements.
 */
public abstract class FieldStmt<L extends LValue, R extends RValue> extends AssignStmt<L, R> {

    FieldStmt(L lvalue, R rvalue) {
        super(lvalue, rvalue);
    }

    public abstract FieldAccess getFieldAccess();

    public FieldRef getFieldRef() {
        return getFieldAccess().getFieldRef();
    }

    public boolean isStatic() {
        return getFieldRef().isStatic();
    }
}
