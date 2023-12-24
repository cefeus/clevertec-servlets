package cache;

import cache.impl.LFUCache;
import cache.impl.LFUCacheFactory;
import cache.impl.LRUCache;
import cache.impl.LRUCacheFactory;
import util.ReadProperties;

import java.util.HashMap;
import java.util.Map;


/**
 * Wrapper-like class for instantiating cache accordingly to values in properties file
 */
public class Cache {
    private static final CacheFactory factory = createFactory();
    private static final Map<String, ICache> cacheMap = new HashMap<>();

    private Cache() {
    }

    /**
     * Retrieves the initialized cache.
     *
     * @return The initialized cache, either LFUCache or LRUCache based on the properties.
     */
    public static ICache getCache(String cacheName) {
        ICache cache = cacheMap.get(cacheName);
        if(cache == null) {
            cache = factory.getCache();
            cacheMap.put(cacheName, cache);
        }
        return cache;
    }

    private static CacheFactory createFactory() {
        String cacheType = ReadProperties.getPropertyByKey("CACHE");
        int cacheCapacity = Integer.parseInt(ReadProperties.getPropertyByKey("CAPACITY"));
        switch(cacheType) {
            case "LFU" -> {return new LFUCacheFactory(cacheCapacity);}
            case "LRU" -> {return new LRUCacheFactory(cacheCapacity);}
            default -> throw new IllegalArgumentException("Cannot instantiate cache of type" + cacheType);
        }
    }
}
