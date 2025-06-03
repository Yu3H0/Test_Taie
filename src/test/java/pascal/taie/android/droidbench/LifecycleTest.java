package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LifecycleTest extends DroidBenchTest {

    static final String CATEGORY = "Lifecycle";

    /**
     * Tests for Lifecycle CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ActivityEventSequence1",
            "ActivityEventSequence2",
            "ActivityEventSequence3",
            "ActivityLifecycle1",
            "ActivityLifecycle2",
            "ActivityLifecycle3",
            "ActivityLifecycle4",
            "ActivitySavedState1",
            "ApplicationLifecycle1",
            "ApplicationLifecycle2",
            "ApplicationLifecycle3",
            "AsynchronousEventOrdering1",
            "BroadcastReceiverLifecycle1",
            "BroadcastReceiverLifecycle2",
            "BroadcastReceiverLifecycle3",
            "EventOrdering1",
            "FragmentLifecycle1",
            "FragmentLifecycle2",
            "ServiceEventSequence1",
            "ServiceEventSequence2",
            "ServiceEventSequence3",
            "ServiceLifecycle1",
            "ServiceLifecycle2",
            "SharedPreferenceChanged1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
