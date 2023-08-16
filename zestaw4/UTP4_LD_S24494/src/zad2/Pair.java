package zad2;

import java.util.Objects;

public class Pair<K, V> {

    private K key;
    private V value;
    private int keyHashCodeCache; // cache kodu haszującego klucza

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}