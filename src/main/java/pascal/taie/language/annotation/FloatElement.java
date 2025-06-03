

package pascal.taie.language.annotation;

public record FloatElement(float value) implements Element {

    @Override
    public String toString() {
        return Float.toString(value);
    }
}
