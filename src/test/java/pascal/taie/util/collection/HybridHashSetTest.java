

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;

import java.util.Set;

public class HybridHashSetTest extends AbstractSetTest {

    @Override
    protected <E> Set<E> newSet() {
        return new HybridHashSet<>();
    }

    @Test
    void testAdd20() {
        testAddNElements(newSet(), 20);
    }
}
