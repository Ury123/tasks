package com.ury;

public class MyHashMap2<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MIN_TREEIFY_CAPACITY = 64;
    private static final int TREEIFY_THRESHOLD = 8;

    private int initialCapacity;
    private float loadFactor;
    private int size = 0;
    public AbstractNode<K, V>[] buckets;

    public MyHashMap2() {
        initialCapacity = DEFAULT_INITIAL_CAPACITY;
        loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = new AbstractNode[initialCapacity];
    }

    public MyHashMap2(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new AbstractNode[initialCapacity];
    }

    abstract static class AbstractNode<K, V> {

        final K key;
        V value;

        public AbstractNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    static class Node<K, V> extends AbstractNode<K, V> {
        Node<K, V> next;

        public Node(K key, V value) {
            super(key, value);
        }
    }

    static class TreeNode<K, V> extends AbstractNode<K, V> {

        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> parent;

        public TreeNode(K key, V value, TreeNode<K, V> parent) {
            super(key, value);
            this.parent = parent;
        }

        public TreeNode<K, V> insert(K key, V value) {
            int cmp = ((Comparable<K>) key).compareTo(this.key);

            if (cmp < 0) {
                if (this.left == null) {
                    this.left = new TreeNode(key, value, this);
                } else {
                    this.left.insert(key, value);
                }
            } else if (cmp > 0) {
                if (this.right == null) {
                    this.right = new TreeNode(key, value, this);
                } else {
                    this.right.insert(key, value);
                }
            } else {
                this.value = value;
            }

            return this;
        }

        public TreeNode<K, V> find(K key) {
            int cmp = ((Comparable<K>) key).compareTo(this.key);

            if (cmp == 0) {
                return this;
            } else if (cmp < 0) {
                if (this.left != null) {
                    return this.left.find(key);
                }
            } else if (cmp > 0) {
                if (this.right != null) {
                    return this.right.find(key);
                }
            }

            return null;
        }
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        int index;

        if (isKeyNull(key)) {
            index = 0;
        } else {
            index = hash(key);
        }

        AbstractNode<K, V> node = new Node<>(key, value);

        if (buckets[index] == null) {
            buckets[index] = node;
        } else {

            AbstractNode<K, V> current = buckets[index];
            int count = 0;

            if (current instanceof TreeNode) {
                ((TreeNode<K, V>) current).insert(key, value);
                size++;
                return;
            }

            while (current instanceof Node) {
                Node<K, V> nodeCurrent = (Node<K, V>) current;
                if (nodeCurrent.key.equals(key)) {
                    nodeCurrent.value = value;
                    return;
                }
                if (nodeCurrent.next == null) {
                    nodeCurrent.next = (Node<K, V>) node;
                    count++;
                    break;
                }
                current = nodeCurrent.next;
                count++;
            }

            if (buckets.length >= MIN_TREEIFY_CAPACITY && count >= TREEIFY_THRESHOLD) {
                treeifyBin(index);
            }
        }

        size++;

        if (size / buckets.length > loadFactor) {
            resize();
        }
    }

    public V get(K key) {
        int index = hash(key);
        AbstractNode<K, V> current = buckets[index];

        if (current instanceof TreeNode) {
            TreeNode<K, V> node = ((TreeNode<K, V>) current).find(key);
            if (node != null) {
                return node.value;
            } else {
                return null;
            }
        } else {
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = ((Node<K, V>) current).next;
            }
        }

        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        AbstractNode<K, V> current = buckets[index];
        Node<K, V> prev = null;

        if (current instanceof TreeNode) {
            TreeNode<K, V> treeNode = ((TreeNode<K, V>) current);
            TreeNode<K, V> target = treeNode.find(key);
            if (target != null) {
                target.value = null;
                size--;
            }
            return;
        } else {
            while(current != null) {
                if (current.key.equals(key)) {
                    if (prev == null) {
                        buckets[index] = ((Node<K, V>) current).next;
                    } else {
                        prev.next = ((Node<K, V>) current).next;
                    }
                    size--;
                    return;
                }
                prev = (Node<K, V>) current;
                current = ((Node<K, V>) current).next;
            }
        }
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    private boolean isKeyNull(K key) {
        return key == null;
    }

    private int hash(K key) {
        return key.hashCode() % buckets.length;
    }

    private void resize() {
        AbstractNode<K, V>[] oldBuckets = buckets;
        buckets = new AbstractNode[oldBuckets.length * 2];
        size = 0;

        for (AbstractNode<K, V> node : oldBuckets) {
            while (node != null) {
                if (node instanceof TreeNode) {
                    rebuildTree((TreeNode<K, V>) node);
                } else {

                }
                put(node.key, node.value);
                node = ((Node<K, V>) node).next;
            }
        }
    }

    private void rebuildTree(TreeNode<K, V> treeNode) {
        if (treeNode == null) return;
        put(treeNode.key, treeNode.value);
        rebuildTree(treeNode.left);
        rebuildTree(treeNode.right);
    }

    private void treeifyBin(int index) {
        Node<K, V> node = (Node<K, V>) buckets[index];
        TreeNode<K, V> root = new TreeNode<>(node.key, node.value, null);
        node = node.next;

        while (node != null) {
            root.insert(node.key, node.value);
            node = node.next;
        }

        buckets[index] = root;
    }
}
