package task59;

import java.util.Arrays;

/**
 * 1143. 最长公共子序列
 */
public class Solution1143 {
    public int longestCommonSubsequence(String text1, String text2) {
        char[] arr1 = text1.toCharArray();
        char[] arr2 = text2.toCharArray();
        // 冗余的第一行、第一列用于方便初始化
        int[][] dp = new int[arr1.length + 1][arr2.length + 1];
        int res = 0;

        for (int i = 1; i < arr1.length + 1; i++) {
            for (int j = 1; j < arr2.length + 1; j++) {
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }

                if (dp[i][j] > res) res = dp[i][j];
            }
        }

        for (int[] ints : dp) {
            System.out.println(Arrays.toString(ints));
        }
        return res;
    }

    public static void main(String[] args) {
        // 输入：text1 = "abcde", text2 = "ace"
        // 输出：3
        // 解释：最长公共子序列是 "ace" ，它的长度为 3 。
        int res = new Solution1143().longestCommonSubsequence("abcde", "ace");
        System.out.println(res);

        // int res = new Solution1143().longestCommonSubsequence("abcba", "abcbcba");
        // System.out.println(res);
    }
}
