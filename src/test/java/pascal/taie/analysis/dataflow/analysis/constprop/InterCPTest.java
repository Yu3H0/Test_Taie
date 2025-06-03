

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;
import pascal.taie.analysis.dataflow.inter.InterConstantPropagation;

public class InterCPTest {

    private static final String CLASS_PATH = "src/test/resources/dataflow/constprop/inter";

    void testInterCP(String mainClass) {
        Tests.testMain(mainClass, CLASS_PATH, InterConstantPropagation.ID,
                "edge-refine:false;alias-aware:false", "-a", "cg=algorithm:cha"
                // , "-a", "icfg=dump:true" // <-- uncomment this code if you want
                // to output ICFGs for the test cases
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Example",
            "Reference",
            "Fibonacci",
            "MultiIntArgs",
    })
    void test(String mainClass) {
        testInterCP(mainClass);
    }

}
