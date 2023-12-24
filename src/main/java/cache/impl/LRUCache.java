package cache.impl;

import cache.ICache;
import cache.LinkedHashMapWithCapacity;

import java.util.Optional;

public class LRUCache<K, V> implements ICache<K, V> {

    private final LinkedHashMapWithCapacity<K, V> map;

    public LRUCache(int capacity) {
        this.map = new LinkedHashMapWithCapacity<>(capacity);
    }

    /**
     * Retrieves the value associated with the provided key from the cache.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return Optional containing the value associated with the key if present, else empty Optional.
     */
    public Optional<V> get(K key) {
        V value = this.map.get(key);
        return value == null
                ? Optional.empty()
                : Optional.of(value);
    }

    /**
     * Inserts the key-value pair into the LRU Cache.
     * If the key already exists, updates the value.
     *
     * @param key   The key to be inserted or updated.
     * @param value The value associated with the key.
     */
    public void put(K key, V value) {
        this.map.put(key, value);
    }

    /**
     * Removes the entry associated with the provided key from the LRU Cache.
     *
     * @param key The key of the entry to be removed.
     */
    @Override
    public void pop(K key) {
        map.remove(key);
    }

}
