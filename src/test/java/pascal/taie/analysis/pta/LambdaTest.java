

package pascal.taie.analysis.pta;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pascal.taie.analysis.Tests;

public class LambdaTest {

    private static final String DIR = "lambda";

    private static final String ARG = "handle-invokedynamic:true";

    @ParameterizedTest
    @ValueSource(strings = {
            "Args",
            "LambdaConstructor",
            "LambdaInstanceMethod",
            "LambdaStaticMethod",
            "ImpreciseLambdas",
            "DispatchBugDueToLackOfSubclassCheck",
            "NativeModelWithLambda",
    })
    void test(String mainClass) {
        Tests.testPTA(DIR, mainClass, ARG);
    }

}
