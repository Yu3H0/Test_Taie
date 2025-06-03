

package pascal.taie.ir.exp;

import pascal.taie.language.type.ArrayType;
import pascal.taie.language.type.IntType;

/**
 * Representation of array length expression, e.g., arr.length.
 */
public class ArrayLengthExp implements UnaryExp {

    private final Var base;

    public ArrayLengthExp(Var base) {
        this.base = base;
        assert base.getType() instanceof ArrayType;
    }

    public Var getBase() {
        return base;
    }

    @Override
    public Var getOperand() {
        return base;
    }

    @Override
    public IntType getType() {
        return IntType.INT;
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return base + ".length";
    }
}
