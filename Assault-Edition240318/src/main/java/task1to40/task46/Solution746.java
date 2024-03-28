package task1to40.task46;

/**
 * 746. 使用最小花费爬楼梯
 */
public class Solution746 {
    public int minCostClimbingStairs1(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(cost[i - 1] + dp[i - 1], cost[i - 2] + dp[i - 2]);
        }
        return dp[dp.length - 1];
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        return Math.min(dp[dp.length - 1], dp[dp.length - 2]);
    }

    public static void main(String[] args) {
        // 输入：cost = [10,15,20]
        // 输出：15
        // int res = new Solution746().minCostClimbingStairs(new int[]{10, 15, 20});
        // System.out.println(res);

        // 输入：cost = [1,100,1,1,1,100,1,1,100,1]
        // 输出：6
        int res = new Solution746().minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1});
        System.out.println(res);
    }
}
