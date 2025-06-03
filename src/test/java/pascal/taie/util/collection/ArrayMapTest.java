

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArrayMapTest extends AbstractMapTest {

    @Override
    protected <K, V> Map<K, V> newMap() {
        return new ArrayMap<>();
    }

    @Test
    void testKeySet20() {
        assertThrows(TooManyElementsException.class, super::testKeySet20);
    }
}
