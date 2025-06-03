

package pascal.taie.ir.exp;

import pascal.taie.language.type.PrimitiveType;

import java.util.Set;

/**
 * Representation of unary expression.
 */
public interface UnaryExp extends RValue {

    Var getOperand();

    @Override
    default Set<RValue> getUses() {
        return Set.of(getOperand());
    }

    @Override
    PrimitiveType getType();
}
