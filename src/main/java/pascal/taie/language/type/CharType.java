

package pascal.taie.language.type;

public enum CharType implements PrimitiveType {

    CHAR;

    @Override
    public String toString() {
        return "char";
    }

    @Override
    public boolean asInt() {
        return true;
    }
}
