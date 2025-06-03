

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.FieldAccess;
import pascal.taie.ir.exp.InstanceFieldAccess;
import pascal.taie.ir.exp.Var;

/**
 * Representation of following load field statements:
 * <ul>
 *     <li>load instance field: x = o.f
 *     <li>load static field: x = T.f
 * </ul>
 */
public class LoadField extends FieldStmt<Var, FieldAccess> {

    public LoadField(Var lvalue, FieldAccess rvalue) {
        super(lvalue, rvalue);
        if (rvalue instanceof InstanceFieldAccess) {
            Var base = ((InstanceFieldAccess) rvalue).getBase();
            base.addLoadField(this);
        }
    }

    @Override
    public FieldAccess getFieldAccess() {
        return getRValue();
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
