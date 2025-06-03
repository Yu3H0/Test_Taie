

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CPTestFull extends CPTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "SimpleBoolean",
            "BinaryOp",
            "BranchNAC",
            "BranchUndef",
            "Loop",
            "LogicalOp",
            "DivisionByZero",
            "MultiplyByZero",
            "ConditionOp",
            "ComparisonOp",
    })
    void testFull(String inputCLass) {
        testCP(inputCLass);
    }

}
