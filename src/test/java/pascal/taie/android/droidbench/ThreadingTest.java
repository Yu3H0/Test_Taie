package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ThreadingTest extends DroidBenchTest {

    static final String CATEGORY = "Threading";

    /**
     * Tests for Threading CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "AsyncTask1",
            "Executor1",
            "JavaThread1",
            "JavaThread2",
            "Looper1",
            "TimerTask1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
