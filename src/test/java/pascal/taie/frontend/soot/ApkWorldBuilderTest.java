

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.language.classes.JClass;
import soot.Scene;
import soot.SootClass;

import java.util.Comparator;

public class ApkWorldBuilderTest {

    @Test
    void testWorldBuilder() {
        Main.buildWorld("-am", "-pp", "-cp", "android-benchmarks/DroidBench/apk/Aliasing/FlowSensitivity1.apk");
        World.get()
                .getClassHierarchy()
                .allClasses()
                .sorted(Comparator.comparing(JClass::getName))
                .forEach(jclass -> {
                    SootClass sootClass =
                            Scene.v().getSootClass(jclass.getName());
                    JClassExaminer.examine(jclass, sootClass);
                });
    }
}
