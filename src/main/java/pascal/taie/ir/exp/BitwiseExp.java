

package pascal.taie.ir.exp;

import pascal.taie.language.type.PrimitiveType;

/**
 * Representation of bitwise expression, e.g., a | b.
 */
public class BitwiseExp extends AbstractBinaryExp {

    public enum Op implements BinaryExp.Op {

        OR("|"),
        AND("&"),
        XOR("^"),
        ;

        private final String symbol;

        Op(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    private final Op op;

    public BitwiseExp(Op op, Var value1, Var value2) {
        super(value1, value2);
        this.op = op;
    }

    @Override
    protected void validate() {
        assert (Exps.holdsInt(operand1) && Exps.holdsInt((operand2)) ||
                (Exps.holdsLong(operand1) && Exps.holdsLong(operand2)));
    }

    @Override
    public Op getOperator() {
        return op;
    }

    @Override
    public PrimitiveType getType() {
        return (PrimitiveType) operand1.getType();
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
