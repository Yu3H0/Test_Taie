

package pascal.taie.language.type;

public enum BooleanType implements PrimitiveType {

    BOOLEAN;

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public boolean asInt() {
        return true;
    }
}
