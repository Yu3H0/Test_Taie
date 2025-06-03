

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;
import pascal.taie.util.SerializationUtils;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SetQueueTest {

    @Test
    void test() {
        Queue<Integer> queue = new SetQueue<>();
        queue.addAll(List.of(1, 2, 1, 1, 2, 3, 4));
        assertEquals(4, queue.size());
        assertEquals(1, (int) queue.poll());
        assertEquals(2, (int) queue.poll());
        assertEquals(3, (int) queue.poll());
        assertEquals(4, (int) queue.poll());
    }

    @Test
    void testSerializable() {
        Queue<Integer> queue1 = new SetQueue<>();
        queue1.addAll(List.of(1, 2, 1, 1, 2, 3, 4));
        Queue<Integer> queue2 = SerializationUtils.serializedCopy(queue1);
        assertEquals(queue1.size(), queue2.size());
        while (!queue1.isEmpty()) {
            assertEquals(queue1.poll(), queue2.poll());
        }
    }
}
