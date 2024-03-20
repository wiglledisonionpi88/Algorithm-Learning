import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                7,
                new Solution().maxProfit(new int[]{7,1,5,3,6,4})
        );
    }

    public int maxProfit(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                // 做交易
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    public int maxProfitDp(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        // dp[i] 第i天的最大收益
        int[] dp = new int[prices.length];
        dp[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            dp[i] = Math.max(dp[i - 1] + prices[i] - prices[i - 1], dp[i - 1]);
        }
        return dp[dp.length - 1];
    }

    public int maxProfitDp2(int[] prices) {
        int dp = 0;

        for (int i = 1; i < prices.length; i++) {
            dp = Math.max(dp + prices[i] - prices[i - 1], dp);
        }
        return dp;
    }
}