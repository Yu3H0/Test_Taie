

package pascal.taie.ir.exp;

import pascal.taie.language.type.PrimitiveType;

/**
 * Representation of arithmetic expression, e.g., a + b.
 */
public class ArithmeticExp extends AbstractBinaryExp {

    public enum Op implements BinaryExp.Op {

        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        REM("%"),
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

    public ArithmeticExp(Op op, Var value1, Var value2) {
        super(value1, value2);
        this.op = op;
    }

    @Override
    protected void validate() {
        assert (Exps.holdsInt(operand1) && Exps.holdsInt(operand2)) ||
                operand1.getType().equals(operand2.getType());
        assert Exps.holdsPrimitive(operand1);
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
