

package pascal.taie.analysis.dataflow.analysis;

import org.junit.jupiter.api.Test;
import pascal.taie.analysis.Tests;
import pascal.taie.analysis.dataflow.analysis.availexp.AvailableExpression;

public class AvailExpTest {

    @Test
    void test() {
        Tests.testInput("AvailExp", "src/test/resources/dataflow/",
                AvailableExpression.ID);
    }
}
