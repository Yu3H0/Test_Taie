

package pascal.taie.ir.exp;


import pascal.taie.language.type.IntType;
import pascal.taie.language.type.PrimitiveType;

/**
 * Representation of negation expression, e.g., -o;
 */
public class NegExp implements UnaryExp {

    private final Var value;

    public NegExp(Var value) {
        this.value = value;
        assert value.getType() instanceof PrimitiveType;
    }

    public Var getValue() {
        return value;
    }

    @Override
    public Var getOperand() {
        return value;
    }

    @Override
    public PrimitiveType getType() {
        PrimitiveType type = (PrimitiveType) value.getType();
        return type.asInt() ? IntType.INT : type;
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "-" + value;
    }
}
