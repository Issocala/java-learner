package com.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruExample {
    public static void main(String[] args) {
        LruCache<Integer,Integer> lruCache = new LruCache<Integer, Integer>(4);
        for(int i = 0; i < 10; i++){
            lruCache.put(i,i+"a");
        }

        Iterator iterator = (Iterator) lruCache.entrySet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        lruCache.get(8);
        Iterator iterator1 = lruCache.entrySet().iterator();
        while (iterator1.hasNext()){
            System.out.println("----"+iterator1.next());
        }
    }
}
