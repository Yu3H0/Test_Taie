

package pascal.taie.analysis.pta.plugin.reflection;

import pascal.taie.ir.stmt.Invoke;
import pascal.taie.language.classes.JClass;

import javax.annotation.Nullable;
import java.util.Set;

record MethodInfo(Invoke invoke, @Nullable JClass clazz, @Nullable String name) {

    private static final Set<String> GET_METHOD =
            Set.of("getMethod", "getMethods");

    private static final Set<String> GET_DECLARED_METHOD =
            Set.of("getDeclaredMethod", "getDeclaredMethods");

    boolean isFromGetMethod() {
        return GET_METHOD.contains(invoke.getMethodRef().getName());
    }

    boolean isFromGetDeclaredMethod() {
        return GET_DECLARED_METHOD.contains(invoke.getMethodRef().getName());
    }

    boolean isClassUnknown() {
        return clazz == null;
    }

    boolean isNameUnknown() {
        return name == null;
    }
}
