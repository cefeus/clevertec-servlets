package cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A LinkedHashMap that maintains a specified maximum capacity.
 *
 * @param <K> Type of keys maintained by this map.
 * @param <V> Type of mapped values.
 */
public class LinkedHashMapWithCapacity<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LinkedHashMapWithCapacity(int capacity) {
        super(16, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * Determines if the eldest entry should be removed when a new entry is added.
     *
     * @param eldest The least recently accessed entry in the map.
     * @return True if the eldest entry should be removed to accommodate a new entry, false otherwise.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.capacity;
    }

}
