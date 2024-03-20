import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        int m = 3;
        int n = 7;
        System.out.println(new Solution().uniquePaths(m, n));
    }
    public int uniquePaths(int m, int n) {
//        dp[i][j] 走到第ij时的不同路径数
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[0][j] = 1;
                    continue;
                }
                if (j == 0) {
                    dp[i][0] = 1;
                    continue;
                }

                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}