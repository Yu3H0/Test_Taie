

package pascal.taie.analysis.deadcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class DeadCodeTest {

    void testDCD(String inputClass) {
        Tests.testInput(inputClass, "src/test/resources/deadcode/",
                DeadCodeDetection.ID,
                "-a", "live-var=strongly:false",
                "-a", "const-prop=edge-refine:false");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ControlFlowUnreachable",
            "UnreachableIfBranch",
            "UnreachableSwitchBranch",
            "DeadAssignment",
            "Loops",
    })
    void test(String inputClass) {
        testDCD(inputClass);
    }

}
