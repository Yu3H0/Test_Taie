

package pascal.taie.language.type;

public enum IntType implements PrimitiveType {

    INT;

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean asInt() {
        return true;
    }
}
