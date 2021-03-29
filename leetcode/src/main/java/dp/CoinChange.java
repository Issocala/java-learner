package dp;

import tree.TreeNode;

import java.util.*;

/**
 * 凑零钱问题
 *
 * # 伪码框架
 * def coinChange(coins: List[int], amount: int):
 *     # 定义：要凑出金额 n，至少要 dp(n) 个硬币
 *     def dp(n):
 *         # 做选择，需要硬币最少的那个结果就是答案
 *         for coin in coins:
 *             res = min(res, 1 + dp(n - coin))
 *         return res
 *     # 我们要求目标金额是 amount
 *     return dp(amount)
 */
public class CoinChange {

    public static void main(String[] args) {
        int count = coinChange(new int[]{1, 2, 5}, 11);
        System.out.println(count);
    }

    private static int coinChange(int[] coins, int n) {
        return coinChange(coins, n, new HashMap<>());
    }

    private static int coinChange(int[] coins, int amount, Map<Integer, Integer> amount2CountMap) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        if (amount2CountMap.containsKey(amount)) {
            return amount2CountMap.get(amount);
        }

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int i = coinChange(coins, amount - coin, amount2CountMap);
            if (i < 0) continue;
            res = Math.min(res, i + 1);
        }
        amount2CountMap.put(amount, res == Integer.MAX_VALUE ? -1 : res);
        return amount2CountMap.get(amount);
    }

}
