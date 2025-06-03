

package pascal.taie.analysis.dataflow.analysis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class LiveVarTestFull extends LiveVarTest {

    void testSLV(String inputClass) {
        Tests.testInput(inputClass, "src/test/resources/dataflow/livevar",
                LiveVariable.ID, "strongly:true");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Invoke",
            "Loop",
            "AnonInner",
            "Field",
            "Graph",
            "Sort",
            "ComplexAssign",
            "Corner",
            "GaussianElimination",
            "Switch",
    })
    void testFull(String inputClass) {
        testLV(inputClass);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "StronglyAssign",
            "StronglyBranchLoop",
    })
    void testStrong(String inputClass) {
        testSLV(inputClass);
    }

}
