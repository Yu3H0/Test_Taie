

package pascal.taie.analysis.dataflow;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import pascal.taie.analysis.dataflow.analysis.AvailExpTest;
import pascal.taie.analysis.dataflow.analysis.LiveVarTestFull;
import pascal.taie.analysis.dataflow.analysis.ReachDefTest;
import pascal.taie.analysis.dataflow.analysis.constprop.CPTestSuite;
import pascal.taie.analysis.dataflow.fact.FactTest;

@Suite
@SelectClasses({
        FactTest.class,
        CPTestSuite.class,
        LiveVarTestFull.class,
        ReachDefTest.class,
        AvailExpTest.class,
})
public class DataflowTestSuite {
}
