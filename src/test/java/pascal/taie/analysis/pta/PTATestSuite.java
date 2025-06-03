

package pascal.taie.analysis.pta;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import pascal.taie.analysis.pta.core.cs.context.ContextTest;

@Suite
@SelectClasses({
        ContextTest.class,
        BasicTestFull.class,
        ContextSensitivityTestFull.class,
        ExceptionTest.class,
        LambdaTest.class,
        Java9StringConcatTest.class,
        ReflectionTest.class,
        TaintTest.class,
        WorldCacheTest.class,
})
public class PTATestSuite {
}
