package com.java;

import java.util.Iterator;


public class PutRoom {
    public static void main(String[] args) {
        // 验证利用LinkedHashMap
        LruCache<Integer,String> lruCache = new LruCache<Integer, String>(5);
        for(int i = 1; i < 6; i++){
            lruCache.put(i,i+1);
            System.out.println("第 " + i +" 个人进入房间");
        }
        System.out.print("此时在房间中的人：");
        Iterator iterator = (Iterator) lruCache.keySet().iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        lruCache.get(1);
        System.out.println();
        System.out.println("第1个进入房间的人说话了！");
        lruCache.put(6,"第　" + 6 + " 个人进入了房间");
        System.out.println("第二个进入房间的人被踢了，应为他比剩下的没发言的都早进来！！");
        System.out.print("房间中剩余的人：");
        Iterator peopleAfter = (Iterator) lruCache.keySet().iterator();
        while (peopleAfter.hasNext()){
            System.out.print(peopleAfter.next() + " ");
        }
    }
}
