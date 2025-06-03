

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.UnaryExp;
import pascal.taie.ir.exp.Var;

/**
 * Representation of following kinds of unary assign statements:
 * <ul>
 *     <li>negation: x = -y
 *     <li>array length: x = arr.length
 * </ul>
 */
public class Unary extends AssignStmt<Var, UnaryExp> {

    public Unary(Var lvalue, UnaryExp rvalue) {
        super(lvalue, rvalue);
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
