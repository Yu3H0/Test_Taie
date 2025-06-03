

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import pascal.taie.analysis.Tests;
import pascal.taie.util.MultiStringsSource;


/**
 * Tests context-sensitive pointer analyses.
 */
public class ContextSensitivityTest {

    static final String DIR = "contextsensitivity";

    /**
     * Basic tests
     */
    @ParameterizedTest
    @MultiStringsSource({"OneCall", "cs:1-call",
            "propagate-types:[reference,int]",
            "plugins:[pascal.taie.analysis.pta.plugin.NumberLiteralHandler]"})
    @MultiStringsSource({"OneObject", "cs:1-obj"})
    @MultiStringsSource({"OneType", "cs:1-type"})
    @MultiStringsSource({"TwoCall", "cs:2-call"})
    @MultiStringsSource({"TwoObject", "cs:2-obj"})
    @MultiStringsSource({"TwoType", "cs:2-type"})
    void test(String mainClass, String... opts) {
        Tests.testPTA(DIR, mainClass, opts);
    }

}
