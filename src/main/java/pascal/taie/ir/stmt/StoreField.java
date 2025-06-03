

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.FieldAccess;
import pascal.taie.ir.exp.InstanceFieldAccess;
import pascal.taie.ir.exp.Var;

/**
 * Representation of following store field statements:
 * <ul>
 *     <li>store instance field: o.f = x
 *     <li>store static field: T.f = x
 * </ul>
 */
public class StoreField extends FieldStmt<FieldAccess, Var> {

    public StoreField(FieldAccess lvalue, Var rvalue) {
        super(lvalue, rvalue);
        if (lvalue instanceof InstanceFieldAccess) {
            Var base = ((InstanceFieldAccess) lvalue).getBase();
            base.addStoreField(this);
        }
    }

    @Override
    public FieldAccess getFieldAccess() {
        return getLValue();
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
