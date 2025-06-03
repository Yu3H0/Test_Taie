

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

/**
 * Tests basic functionalities of pointer analysis
 */
public class BasicTest {

    static final String DIR = "basic";

    /**
     * Tests for handling basic pointer analysis statements
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "New",
            "Assign",
            "StoreLoad",
            "Call",
            "Assign2",
            "InstanceField",
            "InstanceField2",
            "CallParamRet",
            "CallField",
            "StaticCall",
            "MergeParam",
            "LinkedQueue",
            "RedBlackBST",
            "MultiReturn",
            "Dispatch",
            "Dispatch2",
            "Interface",
            "Recursion",
            "Cycle",
            "ComplexAssign",
    })
    void test(String mainClass) {
        Tests.testPTA(DIR, mainClass);
    }

}
