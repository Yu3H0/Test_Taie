

package pascal.taie.analysis.defuse;

import org.junit.jupiter.api.Test;
import pascal.taie.analysis.Tests;

public class DefUseTest {

    @Test
    void test() {
        Tests.testInput("DefUse", "src/test/resources/defuse/",
                DefUseAnalysis.ID);
    }
}
