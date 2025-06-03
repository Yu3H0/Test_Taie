

package pascal.taie.language;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pascal.taie.Main;

import static pascal.taie.language.HierarchyTest.testResolveMethod;

public class DefaultMethodTest {

    @BeforeAll
    public static void initTypeManager() {
        Main.buildWorld("-cp", "src/test/resources/world", "--input-classes", "DefaultMethod");
    }

    @Test
    void testDefaultMethod() {
        testResolveMethod("DefaultMethod$C", "foo", "DefaultMethod$A");
        testResolveMethod("DefaultMethod$C", "bar", "DefaultMethod$II");
    }
}
