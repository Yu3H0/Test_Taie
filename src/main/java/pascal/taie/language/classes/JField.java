

package pascal.taie.language.classes;

import pascal.taie.ir.proginfo.FieldRef;
import pascal.taie.language.annotation.AnnotationHolder;
import pascal.taie.language.generics.ReferenceTypeGSignature;
import pascal.taie.language.type.Type;
import pascal.taie.util.Experimental;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Represents fields in the program. Each instance contains various
 * information of a field, including field name, type, declaring class, etc.
 */
public class JField extends ClassMember {

    private final Type type;

    @Nullable
    @Experimental
    private final ReferenceTypeGSignature gSignature;

    public JField(JClass declaringClass, String name, Set<Modifier> modifiers,
                  Type type, @Nullable ReferenceTypeGSignature gSignature,
                  AnnotationHolder annotationHolder) {
        super(declaringClass, name, modifiers, annotationHolder);
        this.type = type;
        this.gSignature = gSignature;
        this.signature = StringReps.getSignatureOf(this);
    }

    public Type getType() {
        return type;
    }

    @Nullable
    @Experimental
    public ReferenceTypeGSignature getGSignature() {
        return gSignature;
    }

    // TODO: more modifiers

    /**
     * @return the {@link FieldRef} pointing to this field.
     */
    public FieldRef getRef() {
        return FieldRef.get(declaringClass, name, type, isStatic());
    }

    @Override
    public String toString() {
        return StringReps.getSignatureOf(this);
    }
}
