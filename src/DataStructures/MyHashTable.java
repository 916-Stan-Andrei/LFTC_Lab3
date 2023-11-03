package DataStructures;

import java.util.ArrayList;
import java.util.List;

public class MyHashTable<K, V> implements IMyHashTable<K, V>{
    private final List<List<K>> myHashTable;
    private final int capacity;

    public MyHashTable(int capacity) {
        this.capacity = capacity;
        this.myHashTable = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            myHashTable.add(new ArrayList<>());
        }
    }
    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int hash(K key) {
        if (key instanceof Integer) {
            return (int) key % capacity;
        } else if (key instanceof String) {
            //djb2 (Daniel J. Bernstein 2) algorithm
            int hashValue = 5381;
            String keyString = (String) key;
            for (int i = 0; i < keyString.length(); i++) {
                char c = keyString.charAt(i);
                hashValue = ((hashValue << 5) + hashValue) + (int) c;
            }
            return Math.abs(hashValue) % capacity;
        }
        return -1;
    }
    @Override
    public boolean contains(K key) {
        int hashValue = getHashValue(key);
        for (K item : myHashTable.get(hashValue)) {
            if (item.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(K key) {
        int hashValue = getHashValue(key);
        myHashTable.get(hashValue).add(key);
    }

    @Override
    public int getHashValue(K key) {
        int hashValue = -1;
        if (key instanceof Integer || key instanceof  String) {
            hashValue = hash(key);
        }
        return hashValue;
    }

    @Override
    public int getBucketIndex(K key) {
        return getHashValue(key);
    }

    @Override
    public String toString() {
        return myHashTable.toString();
    }
}