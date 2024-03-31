package task53;

/**
 * 121. 买卖股票的最佳时机
 */
public class Solution121 {
    public int maxProfit1(int[] prices) {
        int[][] dp = new int[2][prices.length];
        // dp[0][i]：第i天持有的最大收益
        // dp[1][i]：第i天不持有的最大收益

        dp[0][0] = -prices[0];
        dp[1][0] = 0;

        for (int i = 1; i < prices.length; i++) {
            // 昨天持有 或 今天开始持有
            dp[0][i] = Math.max(dp[0][i - 1], -prices[i]);

            dp[1][i] = Math.max(dp[1][i - 1], dp[0][i - 1] + prices[i]);
        }

        return Math.max(dp[0][prices.length - 1], dp[1][prices.length - 1]);
    }

    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            // 等号细节
            if (prices[i] <= min) {
                min = prices[i];
            } else {
                maxProfit = Math.max(maxProfit, prices[i] - min);
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int res = new Solution121().maxProfit(new int[]{7, 1, 5, 3, 6, 4});
        System.out.println(res);
    }
}
