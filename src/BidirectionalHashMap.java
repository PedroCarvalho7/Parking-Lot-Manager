import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BidirectionalHashMap<K, V> {

    private HashMap<K, V> keyToValue = new HashMap<>();
    private HashMap<V, K> valueToKey = new HashMap<>();

    public BidirectionalHashMap() { }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public void put(K key, V value) {
        removeByKey(key);
        removeByValue(value);
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public boolean containsKey(K key) {
        return keyToValue.containsKey(key);
    }

    public boolean containsValue(V value) {
        return valueToKey.containsKey(value);
    }

    public V removeByKey(K key) {
        V value = keyToValue.remove(key);
        if (value != null) {
            valueToKey.remove(value);
        }
        return value;
    }

    public K removeByValue(V value) {
        K key = valueToKey.remove(value);
        if (key != null) {
            keyToValue.remove(key);
        }
        return key;
    }

    public int size() {
        return keyToValue.size();
    }

    public Set<K> keySet() {
        return keyToValue.keySet();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return keyToValue.entrySet();
    }
}
