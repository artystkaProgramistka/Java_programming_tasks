package zad3;

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

    /// @Override
    /// public int compareTo(Pair<K, V> pair2) {
    ///     if (getKey() < pair2.getKey())
    ///         return True;
    ///     else if (getKey() < pair2.getKey())
    ///         return False;
    ///     else {
    ///         if (getValue() < pair2.getValue()) {
    ///             return True;
    ///         } else {
    ///             return False;
    ///         }
    ///     }
    /// }
}