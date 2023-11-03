package DataStructures;

public interface IMyHashTable<K, V> {
    int getCapacity();

    int hash(K key);

    boolean contains(K key);

    void add(K key);

    int getHashValue(K key);

    int getBucketIndex(K key);

    String toString();
}
