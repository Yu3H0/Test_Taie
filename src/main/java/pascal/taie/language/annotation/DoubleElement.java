

package pascal.taie.language.annotation;

public record DoubleElement(double value) implements Element {

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
