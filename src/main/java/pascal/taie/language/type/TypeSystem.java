

package pascal.taie.language.type;

import pascal.taie.language.classes.JClassLoader;

import java.io.Serializable;

/**
 * This class provides APIs for retrieving types in the analyzed program.
 * For convenience, null type, void type and single primitive type
 * can be directly retrieved from their own classes.
 */
public interface TypeSystem extends Serializable {

    Type getType(JClassLoader loader, String typeName);

    Type getType(String typeName);

    ClassType getClassType(JClassLoader loader, String className);

    ClassType getClassType(String className);

    ArrayType getArrayType(Type baseType, int dimensions);

    PrimitiveType getPrimitiveType(String typeName);

    ClassType getBoxedType(PrimitiveType type);

    PrimitiveType getUnboxedType(ClassType type);

    boolean isSubtype(Type supertype, Type subtype);

    boolean isPrimitiveType(String typeName);
}
