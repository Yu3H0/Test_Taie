package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReflectionICCTest extends DroidBenchTest {

    static final String CATEGORY = "Reflection_ICC";

    /**
     * Tests for Reflection_ICC CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ActivityCommunication2",
            "AllReflection",
            "OnlyIntent",
            "OnlyIntentReceive",
            "OnlySMS",
            "OnlyTelephony",
            "OnlyTelephony_Dynamic",
            "OnlyTelephony_Reverse",
            "OnlyTelephony_Substring",
            "SharedPreferences1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
