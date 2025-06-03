

package pascal.taie.ir.stmt;

import pascal.taie.ir.exp.LValue;
import pascal.taie.ir.exp.RValue;
import pascal.taie.util.Indexable;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

/**
 * Representation of statements in Tai-e IR.
 */
public interface Stmt extends Indexable, Serializable {

    /**
     * @return the index of this Stmt in the container IR.
     */
    @Override
    int getIndex();

    void setIndex(int index);

    /**
     * @return the line number of this Stmt in the original source file.
     * If the line number is unavailable, return -1.
     */
    int getLineNumber();

    void setLineNumber(int lineNumber);

    /**
     * @return the (optional) left-value expression defined in this Stmt.
     * In Tai-e IR, each Stmt can define at most one expression.
     */
    Optional<LValue> getDef();

    /**
     * @return a list of right-value expressions used in this Stmt.
     */
    Set<RValue> getUses();

    /**
     * @return true if execution after this statement could continue at
     * the following statement, otherwise false.
     */
    boolean canFallThrough();

    <T> T accept(StmtVisitor<T> visitor);
}
