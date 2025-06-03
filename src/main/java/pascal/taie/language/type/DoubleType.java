

package pascal.taie.language.type;

public enum DoubleType implements PrimitiveType {

    DOUBLE;

    @Override
    public String toString() {
        return "double";
    }

    @Override
    public boolean asInt() {
        return false;
    }
}
