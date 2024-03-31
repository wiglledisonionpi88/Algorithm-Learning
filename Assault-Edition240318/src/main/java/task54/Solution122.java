package task54;

/**
 * 122. 买卖股票的最佳时机 II
 */
public class Solution122 {
    public int maxProfit1(int[] prices) {
        int res = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i]) {
                res += prices[i + 1] - prices[i];
            }
        }
        return res;
    }

    public int maxProfit2(int[] prices) {
        // dp[0] 持有
        // dp[1] 不持有
        int[][] dp = new int[2][prices.length];
        dp[0][0] = -prices[0];
        dp[1][0] = 0;
        for (int i = 1; i < prices.length; i++) {
            // -prices[i]                   今天买入
            // dp[0][i - 1]                 之前买入
            // dp[1][i - 1] - prices[i]     昨天卖出，今天买入（昨天不卖出，今天就不能买入）
            // dp[0][i] = Math.max(-prices[i], Math.max(dp[0][i - 1], dp[1][i - 1] - prices[i]));
            dp[0][i] = Math.max(dp[0][i - 1], dp[1][i - 1] - prices[i]);

            // dp[1][i - 1]                 之前卖出
            // dp[0][i - 1] + prices[i]     之前买入，今天卖出
            dp[1][i] = Math.max(dp[1][i - 1], dp[0][i - 1] + prices[i]);
        }

        return Math.max(dp[0][prices.length - 1], dp[1][prices.length - 1]);
    }

    public int maxProfit(int[] prices) {
        int[] dp = new int[prices.length - 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = prices[i + 1] - prices[i];
        }

        int res = 0;
        for (int j : dp) {
            if (j > 0) res += j;
        }
        return res;
    }
}
