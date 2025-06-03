

package pascal.taie.analysis.graph.callgraph.cha;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CHATestFull extends CHATest {

    @ParameterizedTest
    @ValueSource(strings = {
            "SpecialCall",
            "Interface2",
            "Interface3",
            "Recursion",
            "Recursion2",
            "MaxPQ",
            "LongCallChain",
    })
    void testFull(String mainClass) {
        test(mainClass);
    }

}
