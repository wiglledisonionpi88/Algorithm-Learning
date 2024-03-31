package task55;

import java.util.Arrays;

/**
 * 123. 买卖股票的最佳时机 III
 */
public class Solution123 {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[4][prices.length];
        dp[0][0] = -prices[0];
        dp[2][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            // 一次持有
            dp[0][i] = Math.max(dp[0][i - 1], -prices[i]);
            // 一次卖出
            dp[1][i] = Math.max(dp[1][i - 1], dp[0][i - 1] + prices[i]);

            // 二次持有
            dp[2][i] = Math.max(dp[2][i - 1], dp[1][i - 1] - prices[i]);
            // 二次卖出
            dp[3][i] = Math.max(dp[3][i - 1], dp[2][i - 1] + prices[i]);
        }

        for (int[] ints : dp) {
            System.out.println(Arrays.toString(ints));
        }
        return dp[3][prices.length - 1];
    }

    public static void main(String[] args) {
        int res = new Solution123().maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4});
        System.out.println(res);
    }
}
