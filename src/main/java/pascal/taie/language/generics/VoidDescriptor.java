


package pascal.taie.language.generics;

/**
 * In <a href="https://docs.oracle.com/javase/specs/jvms/se20/html/jvms-4.html#jvms-VoidDescriptor">JVM Spec. 4.3.3 VoidDescriptor</a>,
 * The <i>void</i> descriptor indicates that the method returns no value.
 */
public enum VoidDescriptor implements TypeGSignature {

    VOID;

    public static boolean isVoid(char descriptor) {
        return descriptor == 'V';
    }

    @Override
    public String toString() {
        return "void";
    }
}
