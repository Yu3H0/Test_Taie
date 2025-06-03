

package pascal.taie.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IndexerTest {

    @Test
    void testInit() {
        Indexer<String> i = new SimpleIndexer<>(List.of("aaa", "xxx", "yyy"));
        assertEquals(0, i.getIndex("aaa"));
        assertEquals(0, i.getIndex("aaa"));
        assertEquals(1, i.getIndex("xxx"));
        assertEquals(2, i.getIndex("yyy"));
        assertEquals("aaa", i.getObject(0));
        assertEquals("xxx", i.getObject(1));
        assertEquals("yyy", i.getObject(2));
    }

    @Test
    void testGet() {
        Indexer<String> i = new SimpleIndexer<>();
        assertEquals(0, i.getIndex("0"));
        assertEquals(0, i.getIndex("0"));
        assertEquals(1, i.getIndex("1"));
        assertEquals(2, i.getIndex("2"));
    }

    @Test
    void testGetNull() {
        assertThrows(NullPointerException.class, () ->
                new SimpleIndexer<>().getIndex(null));
    }

    @Test
    void testAbsentId() {
        assertThrows(IllegalArgumentException.class, () ->
                new SimpleIndexer<>().getObject(666));
    }
}
