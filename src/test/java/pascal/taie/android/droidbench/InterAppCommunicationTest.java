package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterAppCommunicationTest extends DroidBenchTest {

    static final String CATEGORY = "InterAppCommunication";

    /**
     * Tests for InterAppCommunication CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "SendSMS-Echoer",
            "StartActivityForResult1-Echoer",
            "DeviceId_Broadcast1-Collector",
            "DeviceId_contentProvider1-Collector",
            "DeviceId_OrderedIntent1-Collector",
            "DeviceId_Service1-Collector",
            "Location1-Collector",
            "Location_Broadcast1-Collector",
            "Location_Service1-Collector"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
