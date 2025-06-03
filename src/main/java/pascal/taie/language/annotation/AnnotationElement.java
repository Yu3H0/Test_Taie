

package pascal.taie.language.annotation;

public record AnnotationElement(Annotation annotation) implements Element {

    @Override
    public String toString() {
        return annotation().toString();
    }
}
