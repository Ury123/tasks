import com.ury.MyHashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyHashMapTest {
    private MyHashMap<String, Integer> map;

    @Test
    public void testPutAndGet() {
        map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put(null, 3);
        map.put(null, 4);
        assertEquals(4, map.get(null));
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertNull(map.get("three"));
    }

    @Test
    public void testUpdateValue() {
        map = new MyHashMap<>();
        map.put("key", 10);
        map.put("key", 20);
        assertEquals(20, map.get("key"));
    }

    @Test
    public void testSize() {
        map = new MyHashMap<>();
        assertEquals(0, map.size());
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(2, map.size());
        map.put("one", 10);
        assertEquals(2, map.size());
    }

    @Test
    public void testRemove() {
        map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put(null, 3);
        map.put(null, 4);
        map.remove("one");
        map.remove(null);
        assertNull(map.get("one"));
        assertNull(map.get(null));
        assertEquals(2, map.get("two"));
        assertEquals(1, map.size());
    }

    @Test
    public void testGetOrDefault() {
        map = new MyHashMap<>();
        map.put("key", 42);
        assertEquals(42, map.getOrDefault("key", 0));
        assertEquals(0, map.getOrDefault("missing", 0));
    }

    @Test
    public void testPutNullKey() {
        map = new MyHashMap<>();
        map.put(null, 100);
        assertEquals(100, map.get(null));
    }

    @Test
    public void testCollisionHandling() {
        MyHashMap<Integer, String> smallMap = new MyHashMap<>(2, 0.75f);
        smallMap.put(1, "A");
        smallMap.put(3, "B");
        assertEquals("A", smallMap.get(1));
        assertEquals("B", smallMap.get(3));
    }
}
