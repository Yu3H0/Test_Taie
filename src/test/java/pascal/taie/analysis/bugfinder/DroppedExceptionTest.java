


package pascal.taie.analysis.bugfinder;

import org.junit.jupiter.api.Test;
import pascal.taie.analysis.Tests;

public class DroppedExceptionTest {

    @Test
    void test() {
        Tests.testInput("DroppedException", "src/test/resources/bugfinder",
                DroppedException.ID);
    }
}
