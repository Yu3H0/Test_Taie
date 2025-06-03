

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewsTest {

    @Test
    void testFilteredCollection() {
        Collection<Integer> view = Views.toFilteredCollection(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), n -> n % 2 == 0);
        IntStream.of(1, 3, 5, 7, 9).forEach(
                n -> assertFalse(view.contains(n)));
        assertEquals(4, view.size());
    }

    @Test
    void testCombinedSet() {
        Set<Integer> view = Views.toCombinedSet(
                Set.of(1, 3, 5, 7, 9), Set.of(2, 4, 6, 8, 10));
        IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(
                n -> assertTrue(view.contains(n)));
        assertEquals(10, view.size());
        int total = 0;
        for (int n : view) {
            total += n;
        }
        assertEquals(55, total);
    }

    @Test
    void testMapSet() {
        Set<Integer> set = Set.of(1, 3, 5);
        Set<Pair<String, Integer>> pairs = Set.of(
                new Pair<>("1", 1),
                new Pair<>("3", 3),
                new Pair<>("5", 5));
        Set<Integer> setView = Views.toMappedSet(pairs, Pair::second);
        assertEquals(setView, set);
    }
}
