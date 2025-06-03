


package pascal.taie.util;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see MultiStringsSource
 */
class MultiStringsSourceArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(
            ExtensionContext context) {
        // check parameters' types
        Method method = context.getRequiredTestMethod();
        Class<?>[] paramTypes = method.getParameterTypes();
        int paramCount = paramTypes.length;
        for (int i = 0; i < paramCount; ++i) {
            Class<?> paramType = paramTypes[i];
            boolean isLastParam = i == paramCount - 1;
            if ((isLastParam && paramType != String.class && paramType != String[].class)
                    || (!isLastParam && paramType != String.class)) {
                throw new IllegalArgumentException(String.format(
                        "Method %s must have only String or String... parameters", method));
            }
        }
        boolean isStringArray = paramTypes[paramCount - 1] == String[].class;
        return context.getElement()
                .map(annotatedElement -> AnnotationSupport.findRepeatableAnnotations(
                        annotatedElement, MultiStringsSource.class))
                .map(List::stream)
                .map(parameterStream -> parameterStream.map(anno ->
                        Arguments.of(normalizeParam(anno.value(), paramCount, isStringArray))))
                .orElse(Stream.empty());
    }

    /**
     * Normalize the parameters to the length of the method parameters.
     */
    private static Object[] normalizeParam(String[] src, int length, boolean isStringArray) {
        Object[] dest = new Object[length];
        if (isStringArray) {
            System.arraycopy(src, 0, dest, 0, length - 1);
            int varargsLength = Math.max(0, src.length - length + 1);
            String[] varargs = new String[varargsLength];
            System.arraycopy(src, length - 1, varargs, 0, varargsLength);
            dest[length - 1] = varargs;
        } else {
            if (src.length > length) {
                throw new IllegalArgumentException(
                        String.format("Argument count mismatch: @MultiStringsValueSource provides %d arguments, "
                                + "but there are only %d parameters", src.length, length));
            } else {
                System.arraycopy(src, 0, dest, 0, src.length);
            }
        }
        return dest;
    }

}
