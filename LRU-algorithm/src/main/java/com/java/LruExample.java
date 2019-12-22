package com.java;

public class LruExample {
    public static void main(String[] args){
        CustomLruCache customLruCache = new CustomLruCache(5);
        for(int i = 0; i < 5; i++){
            customLruCache.put(i,i+"");
        }
        System.out.print("容器key = 0的初始内容：");
        System.out.println(customLruCache.get(0));
        System.out.println("key = 0，被获取了一次，所以会在队头，新增一个，导致key = 1被淘汰");
        customLruCache.put(6,"6");
        System.out.println("里面没有key =　1的，以及被淘汰,索引返回值为" + customLruCache.get(1));
    }
}
