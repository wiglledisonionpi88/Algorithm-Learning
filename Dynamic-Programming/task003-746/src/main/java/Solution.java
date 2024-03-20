import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().minCostClimbingStairs2(new int[]{10,15,20}));
        System.out.println(new Solution().minCostClimbingStairs2(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
    }
    public int minCostClimbingStairs(int[] cost) {
        if (cost.length <= 1) return 0;
//        dp[i] 第i阶花费的最少
        int[] dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }

        System.out.println(Arrays.toString(dp));
        return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }

    public int minCostClimbingStairs2(int[] cost) {
        if (cost.length <= 1) return 0;
//        dp[i] 第i阶花费的最少
//        [10, 15, 30]
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;

        for (int i = 2; i < cost.length + 1; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        System.out.println(Arrays.toString(dp));
        return dp[cost.length];
    }
}