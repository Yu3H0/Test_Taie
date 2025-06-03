

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import pascal.taie.analysis.Tests;
import pascal.taie.util.MultiStringsSource;

public class BasicTestFull extends BasicTest {

    /**
     * Tests for handling of more Java features
     */
    @ParameterizedTest
    @MultiStringsSource("StaticField")
    @MultiStringsSource("Array")
    @MultiStringsSource("Cast")
    @MultiStringsSource("Cast2")
    @MultiStringsSource("Null")
    @MultiStringsSource("Primitive")
    @MultiStringsSource({"Primitives", "propagate-types:[reference,int,double]",
            "plugins:[pascal.taie.analysis.pta.plugin.NumberLiteralHandler]"})
    @MultiStringsSource({"PropagateNull", "propagate-types:[reference,null]",
            "plugins:[pascal.taie.analysis.pta.plugin.NullHandler]"})
    @MultiStringsSource({"Strings", "distinguish-string-constants:all"})
    @MultiStringsSource("MultiArray")
    @MultiStringsSource("Clinit")
    @MultiStringsSource("ClassObj")
    @MultiStringsSource("Native")
    @MultiStringsSource({"NativeModel", "distinguish-string-constants:all"})
    @MultiStringsSource({"Annotations", "cs:1-call",
            "distinguish-string-constants:all"})
    void testFull(String mainClass, String... opts) {
        Tests.testPTA(DIR, mainClass, opts);
    }

}
