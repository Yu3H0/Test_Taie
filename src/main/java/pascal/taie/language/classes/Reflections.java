

package pascal.taie.language.classes;

import pascal.taie.util.collection.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Static utility methods for modeling the behaviors of reflection APIs.
 */
public final class Reflections {

    private Reflections() {
    }

    public static Stream<JMethod> getDeclaredConstructors(JClass jclass) {
        return jclass.getDeclaredMethods()
                .stream()
                .filter(JMethod::isConstructor);
    }

    public static Stream<JMethod> getConstructors(JClass jclass) {
        return getDeclaredConstructors(jclass).filter(ClassMember::isPublic);
    }

    public static Stream<JMethod> getDeclaredMethods(JClass jclass, String methodName) {
        return jclass.getDeclaredMethods()
                .stream()
                .filter(m -> m.getName().equals(methodName));
    }

    public static Stream<JMethod> getDeclaredMethods(JClass jclass) {
        return jclass.getDeclaredMethods()
                .stream()
                .filter(m -> !m.isConstructor() && !m.isStaticInitializer());
    }

    public static Stream<JMethod> getMethods(JClass jclass, String methodName) {
        List<JMethod> methods = new ArrayList<>();
        Set<Subsignature> subSignatures = Sets.newHybridSet();
        while (jclass != null) {
            jclass.getDeclaredMethods()
                    .stream()
                    .filter(m -> m.isPublic() && m.getName().equals(methodName))
                    .filter(m -> !subSignatures.contains(m.getSubsignature()))
                    .forEach(m -> {
                        methods.add(m);
                        subSignatures.add(m.getSubsignature());
                    });
            jclass = jclass.getSuperClass();
        }
        return methods.stream();
    }

    public static Stream<JMethod> getMethods(JClass jclass) {
        List<JMethod> methods = new ArrayList<>();
        Set<Subsignature> subSignatures = Sets.newHybridSet();
        while (jclass != null) {
            jclass.getDeclaredMethods()
                    .stream()
                    .filter(JMethod::isPublic)
                    .filter(m -> !m.isConstructor() && !m.isStaticInitializer())
                    .filter(m -> !subSignatures.contains(m.getSubsignature()))
                    .forEach(m -> {
                        methods.add(m);
                        subSignatures.add(m.getSubsignature());
                    });
            jclass = jclass.getSuperClass();
        }
        return methods.stream();
    }
}
