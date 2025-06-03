package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UnreachableCodeTest extends DroidBenchTest {

    static final String CATEGORY = "UnreachableCode";

    /**
     * Tests for UnreachableCode CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "SimpleUnreachable1",
            "UnreachableBoth",
            "UnreachableSink1",
            "UnreachableSource1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
