

package pascal.taie.language.annotation;

public record BooleanElement(boolean value) implements Element {

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
