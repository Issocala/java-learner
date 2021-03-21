package com.student.base.struct;

import java.util.*;

/**
 * @author : luoyong
 * @date : 2021-03-21 14:18
 **/
public class Tree<K, E> {
    public static final Integer ROOT_LEVEL = 1;

    private TreeChildOrder childOrder;

    private TreeNode<K, E> root;

    private Map<K, TreeNode<K, E>> cache;

    public Tree() {
        this.cache = new HashMap<>();
        childOrder = TreeChildOrder.SimpleTreeChildOrder.UNORDERED;
    }

    public Tree(int size) {
        this.cache = new HashMap<>(size);
        childOrder = TreeChildOrder.SimpleTreeChildOrder.UNORDERED;
    }

    public TreeChildOrder getChildOrder() {
        return childOrder;
    }

    public void setChildOrder(TreeChildOrder childOrder) {
        this.childOrder = childOrder;
    }

    public E getData(K key) {
        TreeNode<K, E> node = getNode(key);
        return node == null ? null : node.getData();
    }

    public TreeNode<K, E> getNode(K key) {
        return this.cache.get(key);
    }

    /**
     * 擦除数据，节点还在
     *
     * @param parentKey
     * @param key       关键字
     * @return 旧值
     */
    public E erase(K parentKey, K key) {
        TreeNode<K, E> parent = getNode(parentKey);
        parent.childDataMap.remove(key);

        TreeNode<K, E> node = this.getNode(key);
        if (node == null) {
            return null;
        }

        E data = node.getData();
        node.data = null;
        return data;
    }

    public E replace(K parentKey, K key, E data) {
        if (data == null) {
            return erase(parentKey, key);
        }

        TreeNode<K, E> replaceTarget = this.cache.get(key);
        E oldData = null;
        if (replaceTarget == null) {
            addChild(parentKey, key, data);
        } else {
            updateChild(parentKey, key, data);
        }
        return oldData;
    }

    private void updateChild(K parentKey, K key, E data) {
        TreeNode<K, E> parent = this.cache.get(parentKey);
        parent.childMap.get(key).data = data;
        parent.childDataMap.put(key, data);
    }

    private void addChild(K parentKey, K key, E data) {
        TreeNode<K, E> parent = this.cache.get(parentKey);
        if (parent == null) {
            throw new RuntimeException("no parent node : " + parentKey);
        }
        TreeNode<K, E> treeNode = new TreeNode<>(key, data, this.childOrder);
        parent.addChild(treeNode);
        this.cache.put(key, treeNode);
    }

    public ChildToRootIterator<K, E> iteratorFromChildToRoot(K key) {
        TreeNode<K, E> node = this.cache.get(key);
        if (node == null) {
            return null;
        }

        return new ChildToRootIterator<>(node);
    }

    public void setRoot(K key, E data) {
        this.root = new TreeNode<>(key, data, this.childOrder);
        this.cache.put(root.getKey(), root);
    }

    public E getRootData() {
        return this.root.data;
    }

    /**
     * 获取节点所在层数，root为level1,每向下一层，则level + 1
     *
     * @param key
     * @return -1 节点不存在
     */
    public int getLevel(K key) {
        TreeNode<K, E> node = this.cache.get(key);
        if (node == null) {
            return -1;
        }
        if (node.getParent() == null) {
            return ROOT_LEVEL;
        }

        return getLevel(node.getParent().getKey()) + 1;
    }

    public boolean containKey(K parentKey) {
        return cache.containsKey(parentKey);
    }

    public Set<K> keySet() {
        return cache.keySet();
    }

    /**
     * 树结点数据结构
     *
     * @param <K>
     * @param <E>
     */
    public static class TreeNode<K, E> {
        /**
         * 关键词
         */
        K key;
        /**
         * 数据
         */
        E data;
        /**
         * 父节点
         */
        TreeNode<K, E> parent = null;
        /**
         * 子节点集合
         */
        Map<K, TreeNode<K, E>> childMap;

        /**
         * 子节点数据列表
         */
        Map<K, E> childDataMap;

        /**
         * 是否为根节点
         */
        boolean rootNode = true;

        /**
         * 是否为叶子节点
         */
        boolean leafNode = true;

        public TreeNode(K key, E data, TreeChildOrder childOrder) {
            this.key = key;
            this.data = data;
            this.childMap = childOrder.createChildMap();
            this.childDataMap = childOrder.createChildMap();
        }

        public K getKey() {
            return key;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public TreeNode<K, E> getParent() {
            return parent;
        }

        public Map<K, TreeNode<K, E>> getChildMap() {
            return childMap;
        }

        /**
         * 设置父节点
         *
         * @param parent
         */
        void setParent(TreeNode<K, E> parent) {
            this.parent = parent;
            this.rootNode = this.parent == null;
        }

        public boolean isLeafNode() {
            return leafNode;
        }

        public boolean isRootNode() {
            return rootNode;
        }

        /**
         * 添加子节点
         *
         * @param node
         */
        void addChild(TreeNode<K, E> node) {
            node.setParent(this);
            this.childMap.put(node.key, node);
            this.childDataMap.put(node.key, node.data);
            this.leafNode = this.childMap.size() == 0;
        }

        /**
         * 移除子节点
         *
         * @param key
         * @return
         */
        public TreeNode<K, E> removeChild(K key) {
            TreeNode<K, E> remove = this.childMap.remove(key);
            if (remove != null) {
                remove.setParent(null);
                this.childDataMap.remove(key);
            }
            this.leafNode = this.childMap.size() == 0;
            return remove;
        }

        /**
         * 获取所有子节点数据
         *
         * @return
         */
        public Collection<E> getAllChildData() {
            return childDataMap.values();
        }

        public Map<K, E> getChildDataMap() {
            return childDataMap;
        }

        /**
         * 获取node下的所有叶子节点
         *
         * @return
         */
        public List<TreeNode<K, E>> getAllLeafNodes() {
            List<TreeNode<K, E>> result = new ArrayList<>();
            getLeafNode(result, null);
            return result;
        }

        private void getLeafNode(List<TreeNode<K, E>> list, TreeNode<K, E> node) {
            if (node.isLeafNode()) {
                list.add(node);
            } else {
                Map<K, TreeNode<K, E>> childMap = node.getChildMap();
                childMap.values().forEach(treeNode -> getLeafNode(list, treeNode));
            }
        }
    }

    /**
     * 深度遍历
     *
     * @param function
     */
    public void forEach(TreeConsumer<K, E> function) {
        function.accept(root.getKey(), root.getData());
        forEach(function, root);
    }

    public void forEach(TreeConsumer<K, E> function, TreeNode<K, E> node) {
        for (TreeNode<K, E> child : node.childMap.values()) {
            function.accept(child.getKey(), child.getData());
            forEach(function, child);
        }
    }

    public static class ChildToRootIterator<K, E> implements Iterator<TreeNode> {
        TreeNode<K, E> cur;

        ChildToRootIterator(TreeNode<K, E> cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public TreeNode<K, E> next() {
            if (cur == null) {
                return null;
            }
            TreeNode<K, E> next = cur;
            cur = cur.parent;
            return next;
        }
    }

    public interface TreeConsumer<K, E> {
        /**
         * 根据参数执行逻辑
         *
         * @param key
         * @param data
         */
        void accept(K key, E data);
    }

    public interface TreeVisitor<K, E, R> {
        /**
         * 节点访问，带返回结果
         *
         * @param key
         * @param data
         * @return
         */
        R visit(K key, E data);
    }
}
