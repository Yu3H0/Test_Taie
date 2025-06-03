

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;
import pascal.taie.util.SerializationUtils;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ALL")
abstract class AbstractSetTest {

    protected abstract <E> Set<E> newSet();

    @Test
    void testAdd() {
        Set<String> set = newSet();
        set.add("a");
        set.add("a");
        set.add("b");
        set.add("c");
        assertEquals(3, set.size());
    }

    @Test
    void testAddNull() {
        assertThrows(NullPointerException.class, () -> {
            Set<String> set = newSet();
            set.add("a");
            set.add(null);
            set.add("b");
        });
    }

    void testAddNElements(Set<Integer> set, int n) {
        set.clear();
        for (int i = 0; i < n; ++i) {
            set.add(i);
        }
        assertEquals(n, set.size());
    }

    @Test
    void testAddAll() {
        Set<String> set = newSet();
        set.addAll(Arrays.asList("a", "a", "b", "c", "c"));
        assertEquals(3, set.size());
    }

    @Test
    void testRemove() {
        Set<String> set = newSet();
        set.add("a");
        set.add("b");
        set.add("c");
        assertEquals(3, set.size());
        set.remove("x");
        assertEquals(3, set.size());
        set.remove("a");
        assertEquals(2, set.size());
        set.remove("b");
        assertEquals(1, set.size());
    }

    @Test
    void testEmpty() {
        Set<String> set = newSet();
        assertEquals(0, set.size());
        set.remove("x");
        assertEquals(0, set.size());
    }

    @Test
    void testSerializable() {
        Set<String> set1 = newSet();
        set1.add("a");
        set1.add("b");
        set1.add("c");
        Set<String> set2 = SerializationUtils.serializedCopy(set1);
        assertEquals(set1, set2);
        set1.add("d");
        set2.add("d");
        assertEquals(set1, set2);
    }
}
