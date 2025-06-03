package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SelfModificationTest extends DroidBenchTest {

    static final String CATEGORY = "SelfModification";

    /**
     * Tests for SelfModification CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "BytecodeTamper1",
            "BytecodeTamper2",
            "BytecodeTamper3",
            "BytecodeTamper4"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
