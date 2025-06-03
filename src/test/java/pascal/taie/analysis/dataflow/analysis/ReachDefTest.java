

package pascal.taie.analysis.dataflow.analysis;

import org.junit.jupiter.api.Test;
import pascal.taie.analysis.Tests;

public class ReachDefTest {

    @Test
    void test() {
        Tests.testInput("ReachDef", "src/test/resources/dataflow/",
                ReachingDefinition.ID);
    }
}
