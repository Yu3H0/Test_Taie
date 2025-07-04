

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.ArrayAccess;
import pascal.taie.ir.exp.Var;

/**
 * Representation of load array statement, e.g., x = a[..].
 */
public class LoadArray extends ArrayStmt<Var, ArrayAccess> {

    public LoadArray(Var lvalue, ArrayAccess rvalue) {
        super(lvalue, rvalue);
        rvalue.getBase().addLoadArray(this);
    }

    @Override
    public ArrayAccess getArrayAccess() {
        return getRValue();
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
