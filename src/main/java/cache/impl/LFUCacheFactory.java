package cache.impl;

import cache.CacheFactory;
import cache.ICache;

public class LFUCacheFactory implements CacheFactory {

    private int capacity;
    public LFUCacheFactory(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public ICache getCache() {
        return new LFUCache(capacity);
    }
}
