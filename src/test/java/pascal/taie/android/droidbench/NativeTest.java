package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NativeTest extends DroidBenchTest {

    static final String CATEGORY = "Native";

    /**
     * Tests for Native CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "JavaIDFunction",
            "NativeIDFunction",
            "SinkInNativeCode",
            "SinkInNativeLibCode",
            "SourceInNativeCode"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
