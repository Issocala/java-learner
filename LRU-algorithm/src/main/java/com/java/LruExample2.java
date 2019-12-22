package com.java;

import java.util.Iterator;

public class LruExample2 {
    public static void main(String[] args){
        LruCache2 lruCache2 = new LruCache2(5);
        for(int i = 0; i < 5; i++){
            lruCache2.put(i,i+10);
        }
        System.out.println("容器key = 0的初始内容：");
        System.out.println(lruCache2.get(0));
        System.out.println("key = 0，被获取了一次，所以会在队头，新增一个，导致key = 1被淘汰");
        lruCache2.put(6,16);
        System.out.println("里面没有key =　1的，以及被淘汰,索引返回值为" + lruCache2.get(1));
    }
}
