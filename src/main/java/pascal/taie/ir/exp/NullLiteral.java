

package pascal.taie.ir.exp;

import pascal.taie.language.type.NullType;

public enum NullLiteral implements ReferenceLiteral {

    INSTANCE;

    public static NullLiteral get() {
        return INSTANCE;
    }

    @Override
    public NullType getType() {
        return NullType.NULL;
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "null";
    }
}
