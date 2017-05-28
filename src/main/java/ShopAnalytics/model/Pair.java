package ShopAnalytics.model;

import javafx.beans.property.IntegerProperty;
import lombok.Data;

/**
 * Created by gman0_000 on 24.05.2017.
 */

public class Pair<K, V> {

    private K key;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    private V value;

    public Pair() {}

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
