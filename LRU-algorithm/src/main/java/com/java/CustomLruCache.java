package com.java;
import java.util.Hashtable;
public class CustomLruCache {
    /**
     * 自定义双端链表
     */
    class DLinkedNode {
        int key;
        String value;
        DLinkedNode prev;
        DLinkedNode next;
    }

    /**
     * 链表操作的新增节点，并使用头插法，保持热点数据在头部
     * @param node
     */
    private void addNode(DLinkedNode node) {

        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    /**
     * 链表操作的移除节点
     * @param node
     */
    private void removeNode(DLinkedNode node) {

        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    /**
     * 将节点移动到头部
     * @param node
     */
    private void moveToHead(DLinkedNode node) {

        removeNode(node);
        addNode(node);
    }

    /**
     * 将尾部节点移除
     * @return
     */
    private DLinkedNode popTail() {

        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private Hashtable<Integer, DLinkedNode> cache =
            new Hashtable<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public CustomLruCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 表头
        head = new DLinkedNode();
        // 表尾
        tail = new DLinkedNode();

        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取节点，不存在返回-1
     * 存在移动到链表头部并返回
     * @param key
     * @return
     */
    public String get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return "-1";

        moveToHead(node);

        return node.value;
    }

    /**
     * 插入节点，保证热点在头部
     * @param key
     * @param value
     */
    public void put(int key, String value) {
        DLinkedNode node = cache.get(key);
        // 插入节点不存在
        if (node == null) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            cache.put(key, newNode);
            addNode(newNode);

            ++size;
            // 节点数大于最大容量，移除非热点数据
            if (size > capacity) {
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
            // 插入节点已存在，更新值并移动到链表头部
        } else {
            node.value = value;
            moveToHead(node);
        }
    }
}
