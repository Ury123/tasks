import com.ury.MyHashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyHashMapTest {

    private MyHashMap<Writer, Book[]> map;

    @Test
    public void testPutAndGetShouldWorkCorrectly() {
        map = new MyHashMap<>();

        Writer writer1 = new Writer("John");
        Writer writer2 = new Writer("Jane");
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(writer1, books1);
        map.put(writer2, books2);
        assertEquals(books1, map.get(writer1));
        assertEquals(books2, map.get(writer2));
        assertNull(map.get(new Writer("aaa")));
    }

    @Test
    public void testUpdateValue() {
        map = new MyHashMap<>();

        Writer writer = new Writer("John");
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(writer, books1);
        map.put(writer, books2);
        assertEquals(books2, map.get(writer));
    }

    @Test
    public void testSize() {
        map = new MyHashMap<>();
        assertEquals(0, map.size());
        Writer writer1 = new Writer("John");
        Writer writer2 = new Writer("Jane");
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(writer1, books1);
        map.put(writer2, books2);
        assertEquals(2, map.size());
        map.put(writer1, new Book[] {});
        assertEquals(2, map.size());
    }

    @Test
    public void testRemove() {
        map = new MyHashMap<>();
        Writer writer1 = new Writer("John");
        Writer writer2 = new Writer("Jane");
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(writer1, books1);
        map.put(writer2, books2);
        map.put(null, books2);
        map.put(null, books1);
        map.remove(writer1);
        map.remove(null);
        assertNull(map.get(writer1));
        assertNull(map.get(null));
        assertEquals(books2, map.get(writer2));
        assertEquals(1, map.size());
    }

    @Test
    public void testGetOrDefault() {
        map = new MyHashMap<>();
        Writer writer1 = new Writer("John");
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(writer1, books1);
        assertEquals(books1, map.getOrDefault(writer1, books2));
        assertEquals(books2, map.getOrDefault(new Writer("aaa"), books2));
    }

    @Test
    public void testPutNullKey() {
        map = new MyHashMap<>();
        Book[] books1 = new Book[] {new Book("book1"), new Book("book2")};
        Book[] books2 = new Book[] {new Book("book3"), new Book("book4")};
        map.put(null, books1);
        map.put(null, books2);
        assertEquals(books2, map.get(null));
    }
}
