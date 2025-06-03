

package pascal.taie.analysis.dataflow.analysis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class LiveVarTest {

    void testLV(String inputClass) {
        Tests.testInput(inputClass, "src/test/resources/dataflow/livevar",
                LiveVariable.ID, "strongly:false");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Assign",
            "Branch",
            "BranchLoop",
            "Array",
            "Fibonacci",
            "Reference",
    })
    void test(String inputClass) {
        testLV(inputClass);
    }

}
