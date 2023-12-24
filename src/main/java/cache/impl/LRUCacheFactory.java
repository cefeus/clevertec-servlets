package cache.impl;

import cache.CacheFactory;
import cache.ICache;

public class LRUCacheFactory implements CacheFactory {

    private int capacity;

    public LRUCacheFactory(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public ICache getCache() {
        return new LRUCache(capacity);
    }
}
