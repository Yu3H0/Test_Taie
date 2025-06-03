

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class CPTest {

    void testCP(String inputClass) {
        Tests.testInput(inputClass, "src/test/resources/dataflow/constprop/",
                ConstantPropagation.ID, "edge-refine:false");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Assign",
            "SimpleConstant",
            "SimpleBinary",
            "SimpleBranch",
            "SimpleChar",
            "BranchConstant",
            "Interprocedural",
    })
    void test(String inputClass) {
        testCP(inputClass);
    }

}
