

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.ArrayAccess;
import pascal.taie.ir.exp.Var;

/**
 * Representation of store array statement, e.g., a[..] = x.
 */
public class StoreArray extends ArrayStmt<ArrayAccess, Var> {

    public StoreArray(ArrayAccess lvalue, Var rvalue) {
        super(lvalue, rvalue);
        lvalue.getBase().addStoreArray(this);
    }

    @Override
    public ArrayAccess getArrayAccess() {
        return getLValue();
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
