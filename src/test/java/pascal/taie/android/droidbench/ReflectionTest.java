package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReflectionTest extends DroidBenchTest {

    static final String CATEGORY = "Reflection";

    /**
     * Tests for Reflection CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "Reflection1",
            "Reflection2",
            "Reflection3",
            "Reflection4",
            "Reflection5",
            "Reflection6",
            "Reflection7",
            "Reflection8",
            "Reflection9"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
