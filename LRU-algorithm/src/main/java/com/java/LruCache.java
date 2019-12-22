package com.java;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 利用LinkedHashMap 自定义实现lru算法
 * @param <K>
 * @param <V>
 */
public class  LruCache<K,V> extends LinkedHashMap {
    private int capacity;
    public LruCache(int capacity){
        super(capacity,0.75F,true);
        this.capacity = capacity;
    }

    public V get(Object key){
        return (V) super.get(key);
    }
    public V put(Object key, Object value){
        V oldValue = (V) super.put(key,value);
        return oldValue;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }
}
