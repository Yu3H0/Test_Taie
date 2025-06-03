package pascal.taie.android.ubcbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.android.AndroidBenchTest;

public class UBCBenchTest extends AndroidBenchTest {

    private static final String BENCHMARK_HOME_PREFIX = "android-benchmarks/UBCBench/apk/";

    @ParameterizedTest
    @ValueSource(strings = {
            "ViewCasting",
            "CastingForward",
            "ConservativeModel1",
            "ConservativeModel2",
            "ConservativeModel3",
            "HardCodedLocationTest",
            "CallbacksIntentHandling",
            "GetClass",
            "SetContentView",
            "CallbacksInFragment",
            "ReflectionOverloaded",
            "ReflectionRes",
            "ReflectionDynamic",
            "GetConstructor",
            "ReturnConstructor",
            "EventOrderingTest",
            "ForName",
            "LocationFieldSensitivity",
            "SendTextMessage",
            "SetGetHint",
            "SharedPreference1",
            "SharedPreference2",
            "SharedPreference3",
            "ContextSensitivity",
            "FieldSensitivity",
            "FlowSensitivity",
            "ObjectSensitivity",
            "PathSensitivity"
    })
    void test(String benchmark) {
        run(BENCHMARK_HOME_PREFIX, benchmark, false);
    }

}
