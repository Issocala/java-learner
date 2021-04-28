package com.student.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luo Yong
 * @date 2021-04-28 21:50
 * @Source 1.0
 */

public class RandomUtilTest {

    @Test
    public void inNOutMWeightRandomTest() {
        Map<String, Integer> map = new HashMap<>();
        for (int j = 0; j < 1000000; j++) {
            List<String> tempList = new ArrayList<>();
            //从当前阶段进度全部任务中按权重随机出指定数量（rewardsRef.getGoal()）
            List<RandomUtil.WeightWrap<String>> weightWrapList = new ArrayList<>();
            for (int i = 1; i <= 10; i += 2) {
                weightWrapList.add(new RandomUtil.WeightWrap<>("" + i, i));
            }
            RandomUtil<String> randomUtil = new RandomUtil<>();
            //调用对应随机函数
            weightWrapList = randomUtil.inNOutMWeightRandom(weightWrapList, 3);
            for (RandomUtil.WeightWrap<String> weightWrap : weightWrapList) {
                tempList.add(weightWrap.getT());
            }
            //将随机出的最终结果进行处理
            for (String s : tempList) {
                if (!map.containsKey(s)) {
                    map.put(s, 1);
                } else {
                    map.put(s, map.get(s) + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
