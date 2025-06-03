

package pascal.taie.language.type;

public enum ShortType implements PrimitiveType {

    SHORT;

    @Override
    public String toString() {
        return "short";
    }

    @Override
    public boolean asInt() {
        return true;
    }
}
