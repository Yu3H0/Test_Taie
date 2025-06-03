

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.jupiter.api.Test;
import pascal.taie.analysis.Tests;

public class EdgeRefineTest {

    @Test
    void test() {
        Tests.testInput("EdgeRefine", "src/test/resources/dataflow/constprop/",
                ConstantPropagation.ID, "edge-refine:true");
    }
}
