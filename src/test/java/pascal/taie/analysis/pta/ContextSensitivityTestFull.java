

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import pascal.taie.analysis.Tests;
import pascal.taie.util.MultiStringsSource;

public class ContextSensitivityTestFull extends ContextSensitivityTest {

    @ParameterizedTest
    // More complex tests
    @MultiStringsSource({"RecursiveObj", "cs:2-obj"})
    @MultiStringsSource({"LongObjContext", "cs:2-obj"})
    @MultiStringsSource({"LongCallContext", "cs:2-call"})
    @MultiStringsSource({"StaticSelect", "cs:2-obj"})
    @MultiStringsSource({"TwoCallOnly", "cs:2-call"})
    @MultiStringsSource({"ObjOnly", "cs:1-obj"})
    @MultiStringsSource({"MustUseHeap", "cs:2-call"})
    @MultiStringsSource({"NestedHeap", "cs:2-obj"})
    @MultiStringsSource({"CallOnly", "cs:1-call"})
    @MultiStringsSource({"LinkedQueue", "cs:2-obj"})
    // Tests for handling of non-normal objects
    @MultiStringsSource({"TypeSens", "cs:2-type"})
    @MultiStringsSource({"SpecialHeapContext", "cs:2-obj"})
    void testFull(String mainClass, String... opts) {
        Tests.testPTA(DIR, mainClass, opts);
    }

}
