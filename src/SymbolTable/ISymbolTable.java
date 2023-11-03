package SymbolTable;

public interface ISymbolTable<K> {
    void addHash(K name);

    boolean hasHash(K name);

    int getPositionHash(K name);

    String toString();
}
