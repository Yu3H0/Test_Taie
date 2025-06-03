package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CallbackTest extends DroidBenchTest {

    static final String CATEGORY = "Callbacks";

    /**
     * Tests for Callbacks CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "AnonymousClass1",
            "Button1",
            "Button2",
            "Button3",
            "Button4",
            "Button5",
            "LocationLeak1",
            "LocationLeak2",
            "LocationLeak3",
            "MethodOverride1",
            "MultiHandlers1",
            "Ordering1",
            "RegisterGlobal1",
            "RegisterGlobal2",
            "Unregister1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
