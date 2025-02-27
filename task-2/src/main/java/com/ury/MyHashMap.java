package com.ury;

public class MyHashMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int initialCapacity;
    private float loadFactor;
    private int size = 0;
    public Node<K, V>[] buckets;

    public MyHashMap() {
        initialCapacity = DEFAULT_INITIAL_CAPACITY;
        loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = new Node[initialCapacity];
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Node[initialCapacity];
    }

    static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        int index;

        resize();

        index = indexOf(hash(key));

        Node<K, V> node = new Node<>(key, value);

        if (buckets[index] == null) {
            buckets[index] = node;
        } else {
            Node<K, V> current = buckets[index];
            while (true) {
                if (key == null) {
                    if (current.key == null) {
                        current.value = value;
                        return;
                    }
                } else if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) {
                    current.next = node;
                    break;
                }
                current = current.next;
            }
        }

        size++;

    }

    public V get(K key) {
        int index = indexOf(hash(key));
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (key == null) {
                if (current.key == null) {
                    return current.value;
                }
            } else if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public void remove(K key) {
        int index = indexOf(hash(key));
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;

        while(current != null) {
            if (key == null) {
                if (current.key == null) {
                    if (prev == null) {
                        buckets[index] = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    size--;
                    return;
                }
            } else if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public V getOrDefault(K key, V defaultValue) {
        int index = indexOf(hash(key));
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return defaultValue;
    }

    private int hash(K key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexOf(int hash) {
        return hash % buckets.length;
    }

    private void resize() {

        int count = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                count++;
            }
        }

        if (count / buckets.length > loadFactor) {
            Node<K, V>[] oldBuckets = buckets;
            buckets = new Node[oldBuckets.length * 2];
            size = 0;

            for (Node<K, V> node : oldBuckets) {
                while (node != null) {
                    put(node.key, node.value);
                    node = node.next;
                }
            }
        }
    }

}
