

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.BinaryExp;
import pascal.taie.ir.exp.Var;

/**
 * Representation of assign statement for binary expression, e.g., a = b + c.
 */
public class Binary extends AssignStmt<Var, BinaryExp> {

    public Binary(Var lvalue, BinaryExp rvalue) {
        super(lvalue, rvalue);
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
