

package pascal.taie.language.type;

public enum ByteType implements PrimitiveType {

    BYTE;

    @Override
    public String toString() {
        return "byte";
    }

    @Override
    public boolean asInt() {
        return true;
    }
}
