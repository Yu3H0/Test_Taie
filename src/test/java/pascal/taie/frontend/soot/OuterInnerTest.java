

package pascal.taie.frontend.soot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SuppressWarnings("ConstantConditions")
public class OuterInnerTest {

    private static ClassHierarchy hierarchy;

    @BeforeAll
    public static void beforeClass() {
        Main.buildWorld("-pp", "-cp", "src/test/resources/world",
                "--input-classes", "OuterInner");
        hierarchy = World.get().getClassHierarchy();
    }

    @Test
    void testOuter() {
        JClass main = hierarchy.getClass("OuterInner");
        assertFalse(main.hasOuterClass());
        assertEquals(2, hierarchy.getDirectInnerClassesOf(main).size());

        JClass inner = hierarchy.getClass("OuterInner$Inner");
        assertTrue(inner.hasOuterClass());

        JClass outer = hierarchy.getClass("OuterInner$Outer");
        assertEquals(3, hierarchy.getDirectInnerClassesOf(outer).size());

        JClass outerInner1 = hierarchy.getClass("OuterInner$Outer$Inner1");
        assertEquals(0, hierarchy.getDirectInnerClassesOf(outerInner1).size());
    }
}
