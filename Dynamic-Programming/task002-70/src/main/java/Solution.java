import java.util.Arrays;

class Solution {
//    假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//    每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

    public static void main(String[] args) {
        System.out.println(new Solution().climbStairs(11));
        System.out.println(new Solution().climbStairs2(11));

    }

    public int climbStairs(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
//            ⾸先是dp[i - 1]，上i-1层楼梯，有dp[i - 1]种⽅法，那么再⼀步跳⼀个台阶不就是dp[i]了么。
//            还有就是dp[i - 2]，上i-2层楼梯，有dp[i - 2]种⽅法，那么再⼀步跳两个台阶不就是dp[i]了么。
        }
        System.out.println(Arrays.toString(dp));
        return dp[n];
    }

    public int climbStairs2(int n) {
        if (n <= 1) return n;

        int[] dp = new int[] { 1, 2 };

        for (int i = 0; i < n - 2; i++) {
            int tmp = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = tmp;
        }
        System.out.println(Arrays.toString(dp));
        return dp[1];
    }
}