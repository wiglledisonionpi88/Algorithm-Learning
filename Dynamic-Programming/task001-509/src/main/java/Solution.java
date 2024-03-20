class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().fib(0));
    }
    public int fib(int n) {
        if (n <= 1) return n;
//        dp[i] 斐波那契数列第i项的值
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int fib2(int n) {
        if (n <= 1) return n;
        int[] dp = new int[] { 0, 1 };

        for (int i = 0; i < n - 1; i++) {
            int tmp = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = tmp;
        }
        return dp[1];
    }
}