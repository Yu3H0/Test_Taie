

package pascal.taie.util.collection;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        GenericBitSetTest.class,
        ArraySetTest.class,
        ArrayMapTest.class,
        HybridHashMapTest.class,
        HybridHashSetTest.class,
        IndexMapTest.class,
        MultiMapTest.class,
        RegularBitSetTest.class,
        SetQueueTest.class,
        SparseBitSetTest.class,
        StreamsTest.class,
        TwoKeyMapTest.class,
        TwoKeyMultiMapTest.class,
        ViewsTest.class,
})
public class CollectionTestSuite {
}
