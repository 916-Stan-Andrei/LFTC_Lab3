package SymbolTable;

import DataStructures.IHashTable;
import DataStructures.MyHashTable;

public class SymbolTable<K> implements ISymbolTable<K> {
    private final IHashTable<K, Object> hashTable;

    public SymbolTable(int size) {
        this.hashTable = new MyHashTable<>(size);
    }

    public void addHash(K name) {
        hashTable.add(name);
    }

    public boolean hasHash(K name) {
        return hashTable.contains(name);
    }

    public int getPositionHash(K name) {
        return hashTable.getBucketIndex(name);
    }

    @Override
    public String toString() {
        return "SymbolTable = { " + hashTable + " }";
    }
}
