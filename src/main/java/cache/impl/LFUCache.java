package cache.impl;

import cache.ICache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;

public class LFUCache<K, V> implements ICache<K, V> {

    private final HashMap<K, V> values;
    private final HashMap<K, Integer> callCount;
    private final HashMap<Integer, LinkedHashSet<K>> callCountsLists;
    int capacity;
    int min = -1;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        values = new HashMap<>();
        callCount = new HashMap<>();
        callCountsLists = new HashMap<>();
        callCountsLists.put(1, new LinkedHashSet<>());
    }

    /**
     * Retrieves the value associated with the provided key from the cache.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return Optional containing the value associated with the key if present, else empty Optional.
     */
    public Optional<V> get(K key) {
        if (!values.containsKey(key))
            return Optional.empty();

        int count = callCount.get(key);
        callCount.put(key, count + 1);
        callCountsLists.get(count).remove(key);
        if (count == min && callCountsLists.get(count).isEmpty())
            min++;
        if (!callCountsLists.containsKey(count + 1))
            callCountsLists.put(count + 1, new LinkedHashSet<>());
        callCountsLists.get(count + 1).add(key);
        return Optional.of(values.get(key));
    }

    /**
     * Inserts the key-value pair into the cache.
     * If the key already exists, updates the value.
     *
     * @param key   The key to be inserted or updated.
     * @param value The value associated with the key.
     */
    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }
        if (values.containsKey(key)) {
            values.put(key, value);
            get(key);
            return;
        }
        if (values.size() >= capacity) {
            K evit = callCountsLists.get(min).iterator().next();
            callCountsLists.get(min).remove(evit);
            values.remove(evit);
            callCount.remove(evit);
        }
        values.put(key, value);
        callCount.put(key, 1);
        min = 1;
        callCountsLists.get(1).add(key);
    }

    /**
     * Removes the entry associated with the provided key from the cache.
     *
     * @param key The key of the entry to be removed.
     */
    @Override
    public void pop(K key) {
        values.remove(key);
        int count = callCount.get(key);
        callCountsLists.get(count).remove(key);
        callCount.remove(key);
    }

}
