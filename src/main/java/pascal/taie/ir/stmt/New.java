

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.NewExp;
import pascal.taie.ir.exp.Var;
import pascal.taie.language.classes.JMethod;

/**
 * Representation of following kinds of new statements:
 * <ul>
 *     <li>new instance: o = new T
 *     <li>new array: o = new T[..]
 *     <li>new multi-array: o = new T[..][..]
 * </ul>
 */
public class New extends AssignStmt<Var, NewExp> {

    /**
     * The method containing this new statement.
     */
    private final JMethod container;

    public New(JMethod method, Var lvalue, NewExp rvalue) {
        super(lvalue, rvalue);
        this.container = method;
    }

    public JMethod getContainer() {
        return container;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
