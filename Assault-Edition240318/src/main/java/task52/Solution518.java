package task52;

import java.util.Arrays;

/**
 * 518. 零钱兑换 II
 */
public class Solution518 {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j < amount + 1; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        // 输入：amount = 5, coins = [1, 2, 5]
        // 输出：4
        // 解释：有四种方式可以凑成总金额
        int res = new Solution518().change(5, new int[]{1, 2, 5});
        System.out.println(res);
    }
}
