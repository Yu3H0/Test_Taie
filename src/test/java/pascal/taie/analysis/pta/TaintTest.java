

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import pascal.taie.analysis.Tests;
import pascal.taie.util.MultiStringsSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TaintTest {

    static final String DIR = "taint";

    static final String TAINT_CONFIG_PREFIX = "taint-config:src/test/resources/pta/taint/";

    static final String TAINT_CONFIG = TAINT_CONFIG_PREFIX + "taint-config.yml";

    @ParameterizedTest
    @MultiStringsSource({"ArrayTaint", TAINT_CONFIG})
    @MultiStringsSource({"CharArray", TAINT_CONFIG})
    @MultiStringsSource({"FieldTaint", TAINT_CONFIG})
    @MultiStringsSource({"LinkedQueue", TAINT_CONFIG})
    @MultiStringsSource({"CSTaint", "cs:1-obj", TAINT_CONFIG})
    @MultiStringsSource({"TwoObjectTaint", "cs:2-obj", TAINT_CONFIG})
    @MultiStringsSource({"TaintCorner", TAINT_CONFIG})
    @MultiStringsSource({"CycleTaint", TAINT_CONFIG})
    @MultiStringsSource({"ComplexTaint", TAINT_CONFIG})
    @MultiStringsSource({"SimpleTaint", TAINT_CONFIG})
    @MultiStringsSource({"ArgToResult", TAINT_CONFIG})
    @MultiStringsSource({"BaseToResult", TAINT_CONFIG})
    @MultiStringsSource({"StringAppend", TAINT_CONFIG})
    @MultiStringsSource({"Java9StringConcat", TAINT_CONFIG})
    @MultiStringsSource({"OneCallTaint", "cs:1-call", TAINT_CONFIG})
    @MultiStringsSource({"InterTaintTransfer", "cs:2-call", TAINT_CONFIG})
    @MultiStringsSource({"TaintInList", "cs:2-obj", TAINT_CONFIG})
    @MultiStringsSource({"BackPropagation", TAINT_CONFIG})
    @MultiStringsSource({"CSBackPropagation", "cs:1-obj", TAINT_CONFIG})
    @MultiStringsSource({"StaticTaintTransfer",
            TAINT_CONFIG_PREFIX + "taint-config-static-taint-transfer.yml"})
    @MultiStringsSource({"InstanceSourceSink",
            TAINT_CONFIG_PREFIX + "taint-config-instance-source-sink.yml"})
    @MultiStringsSource({"ArrayFieldTransfer",
            TAINT_CONFIG_PREFIX + "taint-config-array-field-transfer.yml"})
    @MultiStringsSource({"TaintParam",
            TAINT_CONFIG_PREFIX + "taint-config-param-source.yml"})
    @MultiStringsSource({"TaintCall",
            TAINT_CONFIG_PREFIX + "taint-config-call-source.yml"})
    @MultiStringsSource({"CallSiteMode",
            TAINT_CONFIG_PREFIX + "taint-config-call-site-model.yml"})
    void test(String mainClass, String... opts) {
        testInNonInteractiveMode(mainClass, opts);
        testInInteractiveMode(mainClass, opts);
    }

    private void testInNonInteractiveMode(String mainClass, String... opts) {
        Tests.testPTA(DIR, mainClass, opts);
    }

    private void testInInteractiveMode(String mainClass, String... opts) {
        InputStream originalSystemIn = System.in;
        try {
            String simulatedInput = "r\ne\n";
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            String[] newOpts = new String[opts.length + 1];
            System.arraycopy(opts, 0, newOpts, 0, opts.length);
            newOpts[opts.length] = "taint-interactive-mode:true";
            Tests.testPTA(DIR, mainClass, newOpts);
        } finally {
            System.setIn(originalSystemIn);
        }
    }

}
