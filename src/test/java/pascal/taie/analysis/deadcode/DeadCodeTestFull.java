

package pascal.taie.analysis.deadcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DeadCodeTestFull extends DeadCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "ControlFlowUnreachable2",
            "UnreachableIfBranch2",
            "UnreachableSwitchBranch2",
            "DeadAssignment2",
            "LiveAssignments",
            "MixedDeadCode",
            "NotDead",
            "Corner",
            "AllReachableIfBranch",
            "ForLoops",
            "ArrayField",
    })
    void testFull(String inputClass) {
        testDCD(inputClass);
    }

}
