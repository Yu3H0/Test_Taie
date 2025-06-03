

package pascal.taie.language.annotation;

public record LongElement(long value) implements Element {

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
