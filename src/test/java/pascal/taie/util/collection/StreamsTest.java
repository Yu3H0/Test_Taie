

package pascal.taie.util.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamsTest {

    @Test
    void testConcat() {
        var s1 = Stream.of(1, 2);
        var s2 = Stream.of(3, 4);
        var s3 = Stream.of(5, 6, 7);
        List<Integer> list = new ArrayList<>();
        Streams.concat(s1, s2, s3).forEach(list::add);
        assertEquals(list, List.of(1, 2, 3, 4, 5, 6, 7));
    }
}
