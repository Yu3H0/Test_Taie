


package pascal.taie.language;

import org.junit.jupiter.api.Test;
import pascal.taie.Main;
import pascal.taie.World;
import pascal.taie.language.classes.ClassHierarchy;
import pascal.taie.language.classes.JField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FieldTest {

    @Test
    void testFieldsWithSameName() {
        String clz = "FieldsWithSameName";
        Main.buildWorld("-cp", "src/test/resources/world",
                "--main-class", clz);
        ClassHierarchy hierarchy = World.get().getClassHierarchy();
        JField sf1 = hierarchy.getField("<FieldsWithSameName: java.lang.String a>");
        JField sf2 = hierarchy.getField("<FieldsWithSameName: java.lang.Character a>");
        JField if1 = hierarchy.getField("<FieldsWithSameName: java.lang.String b>");
        JField if2 = hierarchy.getField("<FieldsWithSameName: java.lang.Character b>");
        assertEquals(clz, sf1.getDeclaringClass().getName());
        assertEquals(clz, sf2.getDeclaringClass().getName());
        assertEquals(clz, if1.getDeclaringClass().getName());
        assertEquals(clz, if2.getDeclaringClass().getName());
        assertEquals("a", sf1.getName());
        assertEquals("a", sf2.getName());
        assertEquals("b", if1.getName());
        assertEquals("b", if2.getName());
        assertTrue(sf1.isStatic());
        assertTrue(sf2.isStatic());
        assertFalse(if1.isStatic());
        assertFalse(if2.isStatic());
        assertEquals("java.lang.String", sf1.getType().getName());
        assertEquals("java.lang.Character", sf2.getType().getName());
        assertEquals("java.lang.String", if1.getType().getName());
        assertEquals("java.lang.Character", if2.getType().getName());
    }
}
