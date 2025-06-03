package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ImplicitFlowsTest extends DroidBenchTest {

    static final String CATEGORY = "ImplicitFlows";

    /**
     * Tests for ImplicitFlows CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ImplicitFlow1",
            "ImplicitFlow2",
            "ImplicitFlow3",
            "ImplicitFlow4",
            "ImplicitFlow5",
            "ImplicitFlow6"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
