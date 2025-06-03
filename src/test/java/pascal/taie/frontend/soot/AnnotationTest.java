

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.language.classes.JClass;

public class AnnotationTest {

    private static void buildWorld(String main) {
        Main.buildWorld("-pp", "-cp", "src/test/resources/world", "--input-classes", main);
    }

    @Test
    void testAnnotation() {
        buildWorld("Annotated");
        JClass main = World.get().getClassHierarchy().getClass("Annotated");
        AnnotationPrinter.print(main);
    }

    @Test
    void testAnnotationJava() {
        buildWorld("AnnotatedJava");
        JClass main = World.get().getClassHierarchy().getClass("AnnotatedJava");
        AnnotationPrinter.print(main);
    }
}
