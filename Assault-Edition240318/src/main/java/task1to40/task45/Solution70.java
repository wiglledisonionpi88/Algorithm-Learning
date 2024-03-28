package task1to40.task45;

/**
 * 70. 爬楼梯
 */
public class Solution70 {
    public int climbStairs1(int n) {
        if (n <= 2) return n;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // oot
    // public int climbStairs(int n) {
    //     if (n <= 2) return n;
    //     return climbStairs(n - 1) + climbStairs(n - 2);
    // }

    int[] cache;
    public int climbStairs(int n) {
        cache = new int[n + 1];
        return climbStairsHelper(n);
    }

    private int climbStairsHelper(int n) {
        if (n <= 2) return n;
        if (cache[n - 1] == 0) {
            cache[n - 1] = climbStairsHelper(n - 1);
        }
        if (cache[n - 2] == 0) {
            cache[n - 2] = climbStairsHelper(n - 2);
        }
        return cache[n - 1] + cache[n - 2];
    }
}
