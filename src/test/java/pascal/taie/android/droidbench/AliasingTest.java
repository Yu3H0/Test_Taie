package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AliasingTest extends DroidBenchTest {

    static final String CATEGORY = "Aliasing";

    /**
     * Tests for Aliasing CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "FlowSensitivity1",
            "Merge1",
            "SimpleAliasing1",
            "StrongUpdate1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }
}
