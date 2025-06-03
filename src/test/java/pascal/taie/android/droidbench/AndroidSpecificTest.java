package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AndroidSpecificTest extends DroidBenchTest {

    static final String CATEGORY = "AndroidSpecific";

    /**
     * Tests for AndroidSpecific CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "ApplicationModeling1",
            "DirectLeak1",
            "InactiveActivity",
            "Library2",
            "LogNoLeak",
            "Obfuscation1",
            "Parcel1",
            "PrivateDataLeak1",
            "PrivateDataLeak2",
            "PrivateDataLeak3",
            "PublicAPIField1",
            "PublicAPIField2",
            "View1"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
