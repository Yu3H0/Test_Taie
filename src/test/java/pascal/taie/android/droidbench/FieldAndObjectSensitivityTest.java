package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FieldAndObjectSensitivityTest extends DroidBenchTest {

    static final String CATEGORY = "FieldAndObjectSensitivity";

    /**
     * Tests for FieldAndObjectSensitivity CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "FieldSensitivity1",
            "FieldSensitivity2",
            "FieldSensitivity3",
            "FieldSensitivity4",
            "InheritedObjects1",
            "ObjectSensitivity1",
            "ObjectSensitivity2"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
