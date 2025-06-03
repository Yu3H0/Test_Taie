

package pascal.taie.analysis.graph.callgraph.cha;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class CHATest {

    private static final String CLASS_PATH = "src/test/resources/cha/";

    static void test(String mainClass) {
        Tests.testMain(mainClass, CLASS_PATH, "cg", "algorithm:cha");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "StaticCall",
            "VirtualCall",
            "Interface",
            "AbstractMethod",
    })
    void testFull(String mainClass) {
        test(mainClass);
    }

}
