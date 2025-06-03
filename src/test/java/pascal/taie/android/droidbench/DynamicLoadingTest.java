package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DynamicLoadingTest extends DroidBenchTest {

    static final String CATEGORY = "DynamicLoading";

    /**
     * Tests for DynamicLoading CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "DynamicBoth1",
            "DynamicSink1",
            "DynamicSource1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
