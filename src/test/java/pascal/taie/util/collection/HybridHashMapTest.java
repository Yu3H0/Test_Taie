

package pascal.taie.util.collection;

import java.util.Map;

public class HybridHashMapTest extends AbstractMapTest {

    protected <K, V> Map<K, V> newMap() {
        return new HybridHashMap<>();
    }
}
