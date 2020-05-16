package com.infy.cache;

import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;


public class Cache<K, T> {

    private long timeToLive;
    public static LRUMap cacheMap = new LRUMap(8);

    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected CacheObject(T value) {
            this.value = value;
        }
    }

    public Cache(long elementTimeToLive, final long elementTimerInterval) {
        this.timeToLive = elementTimeToLive * 1000;


        if (timeToLive > 0 && elementTimerInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(elementTimerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cacheMap) {
           CacheObject c = (CacheObject) cacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }
    
    
    
    public static int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    @SuppressWarnings("unchecked")
    public void cleanup() {
    
        ArrayList<K> deleteKey = null;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            //deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            deleteKey = new ArrayList<K>((cacheMap.size()));
            K key = null;
            CacheObject c = null;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CacheObject) itr.getValue();

                //if (c != null && (now > (timeToLive + c.lastAccessed))) {
                if (c != null){
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}

