

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArraySetTest extends AbstractSetTest {

    protected <E> Set<E> newSet() {
        return new ArraySet<>();
    }

    @Test
    void testFixedCapacity() {
        assertThrows(TooManyElementsException.class, () ->
                testAddNElements(new ArraySet<>(4), 5));
    }

    @Test
    void testNonFixedCapacity() {
        testAddNElements(new ArraySet<>(4, false), 5);
    }

    @Test
    void testBoundaryAdd() {
        Set<Integer> s = new ArraySet<>(4);
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(4);
        s.add(1);
    }
}
