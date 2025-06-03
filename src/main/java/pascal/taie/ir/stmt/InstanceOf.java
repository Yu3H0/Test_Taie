

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.InstanceOfExp;
import pascal.taie.ir.exp.Var;

/**
 * Representation of instanceof statement, e.g., a = (b instanceof T).
 */
public class InstanceOf extends AssignStmt<Var, InstanceOfExp> {

    public InstanceOf(Var lvalue, InstanceOfExp rvalue) {
        super(lvalue, rvalue);
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
