

package pascal.taie.analysis.pta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class ExceptionTest {

    private static final String DIR = "exception";

    @ParameterizedTest
    @ValueSource(strings = {
            "ExceptionCircle",
            "ExceptionCircleAndRecursion",
            "ExceptionNoneCaught",
            "ExceptionTreeAndRecursion",
    })
    void test(String mainClass) {
        Tests.testPTA(DIR, mainClass);
    }

    @Test
    void testExceptionFromClinit() {
        Tests.testPTA(DIR, "ExceptionFromClinit", "cs:1-call");
    }

}
