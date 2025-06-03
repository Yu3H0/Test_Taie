

package pascal.taie.frontend.soot;

import pascal.taie.World;
import pascal.taie.language.classes.JClass;
import pascal.taie.language.classes.JField;
import pascal.taie.language.classes.JMethod;
import pascal.taie.language.classes.Subsignature;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JClassExaminer {

    /**
     * Compare the information of JClass and SootClass.
     */
    protected static void examine(JClass jclass, SootClass sootClass) {
        assertTrue(areSameClasses(jclass, sootClass));

        if (!jclass.getName().equals("java.lang.Object")) {
            assertTrue(areSameClasses(
                    jclass.getSuperClass(), sootClass.getSuperclass()));
        }

        assertEquals(jclass.getInterfaces().size(),
                sootClass.getInterfaceCount());

        sootClass.getFields().forEach(sootField -> {
            JField field = jclass.getDeclaredField(sootField.getName());
            assertTrue(areSameFields(field, sootField));
        });

        sootClass.getMethods().forEach(sootMethod -> {
            // Soot signatures are quoted, remove quotes for comparison
            String subSig = sootMethod.getSubSignature().replace("'", "");
            JMethod method = jclass.getDeclaredMethod(Subsignature.get(subSig));
            assertTrue(areSameMethods(method, sootMethod));
        });
    }

    private static boolean areSameClasses(JClass jclass, SootClass sootClass) {
        // phantom class add java.lang.Object to its super class in Tai-e.
        // at the same time, its super class is null in Soot.
        if (World.get().getOptions().isAllowPhantom() && jclass != null && sootClass == null) {
            return true;
        }
        return jclass.getName().equals(sootClass.getName());
    }

    private static boolean areSameFields(JField jfield, SootField sootField) {
        // Soot signatures are quoted, remove quotes for comparison
        return jfield.getSignature()
                .equals(sootField.getSignature().replace("'", ""));
    }

    private static boolean areSameMethods(JMethod jmethod, SootMethod sootMethod) {
        // Soot signatures are quoted, remove quotes for comparison
        return jmethod.getSignature()
                .equals(sootMethod.getSignature().replace("'", ""));
    }
}
