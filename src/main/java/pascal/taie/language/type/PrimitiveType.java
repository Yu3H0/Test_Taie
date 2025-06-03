

package pascal.taie.language.type;

public interface PrimitiveType extends ValueType {

    @Override
    default String getName() {
        return toString();
    }

    /**
     * JVM Spec. (2.11.1): most operations on values of actual types
     * boolean, byte, char, and short are correctly performed by instructions
     * operating on values of computational type int.
     *
     * @return {@code true} if the values of this type are represented
     * as integers in computation.
     */
    boolean asInt();
}
