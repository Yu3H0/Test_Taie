

package pascal.taie.language.type;

public enum FloatType implements PrimitiveType {

    FLOAT;

    @Override
    public String toString() {
        return "float";
    }

    @Override
    public boolean asInt() {
        return false;
    }
}
