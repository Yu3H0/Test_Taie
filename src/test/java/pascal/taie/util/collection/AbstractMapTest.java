

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;
import pascal.taie.util.SerializationUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ALL")
abstract class AbstractMapTest {

    protected abstract <K, V> Map<K, V> newMap();

    @Test
    void testPut() {
        Map<Integer, String> map = newMap();
        map.put(1, "a");
        map.put(1, "b");
        map.put(2, "c");
        map.put(3, "d");
        assertEquals(3, map.size());
        assertEquals("b", map.get(1));
    }

    @Test
    void testPutNullKey() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Object> map = newMap();
            map.put("x", new Object());
            map.put(null, new Object());
        });
    }

    @Test
    void testKeySet() {
        Map<Integer, String> map = newMap();
        Set<Integer> keySet = map.keySet();
        assertEquals(0, keySet.size());
        map.put(1, "x");
        map.put(2, "y");
        assertEquals(2, keySet.size());
        keySet.remove(1);
        assertEquals(1, map.size());
    }

    @Test
    void testKeySet20() {
        testKeySetN(newMap(), 20);
    }

    void testKeySetN(Map<Integer, String> map, int n) {
        map.clear();
        Set<Integer> keySet = map.keySet();
        for (int i = 0; i < n; ++i) {
            map.put(i, "");
        }
        assertEquals(n, keySet.size());
    }

    @Test
    void testKeySetIterator() {
        Map<Integer, String> map = newMap();
        map.put(1, "x");
        map.put(2, "y");
        map.put(3, "z");
        assertEquals(3, map.size());
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> ksIt = keySet.iterator();
        while (ksIt.hasNext()) {
            int n = ksIt.next();
            ksIt.remove();
        }
        assertTrue(map.isEmpty());
    }

    @Test
    void testSerializable() {
        Map<Integer, String> map1 = newMap();
        map1.put(1, "x");
        map1.put(2, "y");
        map1.put(3, "z");
        Map<Integer, String> map2 = SerializationUtils.serializedCopy(map1);
        assertEquals(map1, map2);
        map1.put(4, "zz");
        map2.put(4, "zz");
        assertEquals(map1, map2);
    }
}
