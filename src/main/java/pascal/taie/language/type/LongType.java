

package pascal.taie.language.type;

public enum LongType implements PrimitiveType {

    LONG;

    @Override
    public String toString() {
        return "long";
    }

    @Override
    public boolean asInt() {
        return false;
    }
}
