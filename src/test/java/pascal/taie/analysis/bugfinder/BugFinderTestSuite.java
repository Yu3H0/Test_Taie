

package pascal.taie.analysis.bugfinder;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
        CloneIdiomTest.class,
        DroppedExceptionTest.class,
        IsNullTest.class,
        NullPointerDetectionTest.class,
})
public class BugFinderTestSuite {
}
