package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterComponentCommunicationTest extends DroidBenchTest {

    static final String CATEGORY = "InterComponentCommunication";

    /**
     * Tests for InterComponentCommunication CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ActivityCommunication1",
            "ActivityCommunication2",
            "ActivityCommunication3",
            "ActivityCommunication4",
            "ActivityCommunication5",
            "ActivityCommunication6",
            "ActivityCommunication7",
            "ActivityCommunication8",
            "ActivityCommunication9",
            "BroadcastTaintAndLeak1",
            "ComponentNotInManifest1",
            "EventOrdering1",
//            "IntentSink1",
//            "IntentSink2",
//            "IntentSource1",
            "ServiceCommunication1",
            "ServiceCommunication2",
            "SharedPreferences1",
            "Singletons1",
            "UnresolvableIntent1",
            "IntentExtra",
            "ICCData",
            "ICCData2",
            "Handler",
            "SecondStart",
            "SecondStart2",
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
