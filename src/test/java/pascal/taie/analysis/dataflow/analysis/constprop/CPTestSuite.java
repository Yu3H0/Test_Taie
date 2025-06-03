

package pascal.taie.analysis.dataflow.analysis.constprop;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CPTestFull.class,
        EdgeRefineTest.class,
        InterCPAliasTest.class,
        MeetValueTest.class,
        ValueTest.class,
})
public class CPTestSuite {
}
