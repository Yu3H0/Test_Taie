

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.PhiExp;
import pascal.taie.ir.exp.Var;

/**
 * Representation of phi statements, e.g., x = Φ(x0, x1, ...);
 */
public class Phi extends AssignStmt<Var, PhiExp> {

    public Phi(Var lvalue, PhiExp rvalue) {
        super(lvalue, rvalue);
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
