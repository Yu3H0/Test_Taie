package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmulatorDetectionTest extends DroidBenchTest {

    static final String CATEGORY = "EmulatorDetection";

    /**
     * Tests for EmulatorDetection CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "Battery1",
            "Bluetooth1",
            "Build1",
            "Contacts1",
            "ContentProvider1",
            "DeviceId1",
            "File1",
            "IMEI1",
            "IP1",
            "PI1",
            "PlayStore1",
            "PlayStore2",
            "Sensors1",
            "SubscriberId1",
            "VoiceMail1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
