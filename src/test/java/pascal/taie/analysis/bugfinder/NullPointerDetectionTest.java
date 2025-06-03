


package pascal.taie.analysis.bugfinder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;
import pascal.taie.analysis.bugfinder.nullpointer.NullPointerDetection;

public class NullPointerDetectionTest {

    private static final String folderPath = "src/test/resources/bugfinder";

    @ParameterizedTest
    @ValueSource(strings = {
            "NullDeref",
            "NullDeref2",
            "NullDeref3",
    })
    void testNullPointerException(String inputClass) {
        Tests.testInput(inputClass, folderPath, NullPointerDetection.ID);
    }

}
