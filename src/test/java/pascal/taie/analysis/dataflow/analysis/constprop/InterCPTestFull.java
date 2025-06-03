

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterCPTestFull extends InterCPTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "ArgRet",
            "Call",
            "DeadLoop",
            "FloatArg",
            "MultiReturn",
            "CharArgs",
            "RedBlackBST",
            "PlusPlus",
    })
    void testFull(String mainClass) {
        testInterCP(mainClass);
    }

}
