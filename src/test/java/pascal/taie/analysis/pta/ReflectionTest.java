

package pascal.taie.analysis.pta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class ReflectionTest {

    private static final String DIR = "reflection";

    @Test
    void testBasic() {
        Tests.testPTA(DIR, "GetMember");
    }

    @Test
    void testReflectionLog() {
        Tests.testPTA(DIR, "ReflectiveAction",
                "reflection-inference:null",
                "reflection-log:src/test/resources/pta/reflection/ReflectiveAction.log");
    }

    /**
     * Test cases for Solar
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "LazyHeapModeling",
            "ArgsRefine",
            "GetMethods",
            "UnknownMethodName"
    })
    void testSolar(String mainClass) {
        Tests.testPTA(DIR, mainClass, "reflection-inference:solar");
    }
}
