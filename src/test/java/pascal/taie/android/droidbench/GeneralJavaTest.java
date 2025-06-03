package pascal.taie.android.droidbench;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GeneralJavaTest extends DroidBenchTest {

    static final String CATEGORY = "GeneralJava";

    /**
     * Tests for GeneralJava CATEGORY apk
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "Clone1",
            "Loop1",
            "Serialization1",
            "Loop2",
            "Exceptions1",
            "Exceptions2",
            "Exceptions3",
            "Exceptions4",
            "Exceptions5",
            "Exceptions6",
            "Exceptions7",
            "FactoryMethods1",
            "SourceCodeSpecific1",
            "StartProcessWithSecret1",
            "StaticInitialization1",
            "StaticInitialization2",
            "StaticInitialization3",
            "StringFormatter1",
            "StringPatternMatching1",
            "StringToCharArray1",
            "StringToOutputStream1",
            "UnreachableCode",
            "VirtualDispatch1",
            "VirtualDispatch2",
            "VirtualDispatch3",
            "VirtualDispatch4"
    })
    void test(String benchmark) {
        run(CATEGORY, benchmark);
    }

}
