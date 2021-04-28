package com.student.util;

import java.util.*;

/**
 * <p>
 * 随机工具类
 * </p>
 *
 * @author Luo Yong
 * @date 2021-04-28 11:36
 * @Source 1.0
 */

public class RandomUtil<T> {
    /**
     * <p>
     * A-Res 算法
     * 从集合中按权重随机出m个数
     * 用法：需要先将目标封装为{@link WeightWrap}类
     * 返回的结果需要自行解析里面的T
     * </p>
     *
     * @param sourceList 目标集合
     * @param m          随机出的个数
     * @return 结果集
     */
    public List<WeightWrap<T>> inNOutMWeightRandom(List<WeightWrap<T>> sourceList, int m) {
        if (Objects.isNull(sourceList)) {
            return null;
        }
        if (sourceList.size() <= m) {
            return sourceList;
        }
        PriorityQueue<ResultWrap<T>> questRefPriorityQueue = new PriorityQueue<>(Comparator.comparingDouble(ResultWrap::getK));
        for (WeightWrap<T> weightWrap : sourceList) {
            int w = weightWrap.getWeight();
            double u = Math.random();
            double k = Math.pow(u, 1.0 / w);
            ResultWrap<T> resultWrap = new ResultWrap<>(k, weightWrap);
            if (questRefPriorityQueue.size() < m) {
                questRefPriorityQueue.add(resultWrap);
            } else if (questRefPriorityQueue.size() > 0 && k > questRefPriorityQueue.peek().getK()) {
                questRefPriorityQueue.add(resultWrap);
                if (questRefPriorityQueue.size() > m) {
                    questRefPriorityQueue.poll();
                }
            }
        }
        List<WeightWrap<T>> weightWraps = new ArrayList<>();
        for (ResultWrap<T> resultWrap : questRefPriorityQueue) {
            weightWraps.add(resultWrap.getWeightWrap());
        }
        return weightWraps;
    }

    /**
     * <p>
     * 中间包装类
     * </p>
     *
     * @param <T>
     */
    public static class WeightWrap<T> {
        private T t;
        private int weight;


        public WeightWrap(T t, int weight) {
            this.t = t;
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    /**
     * 结果封装类
     */
    static class ResultWrap<T> {
        private double k;
        private WeightWrap<T> weightWrap;

        public ResultWrap(double k, WeightWrap<T> weightWrap) {
            this.k = k;
            this.weightWrap = weightWrap;
        }

        public double getK() {
            return k;
        }

        public void setK(double k) {
            this.k = k;
        }

        public WeightWrap<T> getWeightWrap() {
            return weightWrap;
        }

        public void setWeightWrap(WeightWrap<T> weightWrap) {
            this.weightWrap = weightWrap;
        }
    }
}
