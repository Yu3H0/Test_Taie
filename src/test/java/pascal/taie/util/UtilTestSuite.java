

package pascal.taie.util;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import pascal.taie.util.collection.CollectionTestSuite;
import pascal.taie.util.graph.GraphTest;

@Suite
@SelectClasses({
        CollectionTestSuite.class,
        GraphTest.class,
        IndexerTest.class,
})
public class UtilTestSuite {
}
