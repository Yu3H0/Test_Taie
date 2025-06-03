

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.ArrayAccess;
import pascal.taie.ir.exp.LValue;
import pascal.taie.ir.exp.RValue;

abstract class ArrayStmt<L extends LValue, R extends RValue> extends AssignStmt<L, R> {

    ArrayStmt(L lvalue, R rvalue) {
        super(lvalue, rvalue);
    }

    public abstract ArrayAccess getArrayAccess();
}
