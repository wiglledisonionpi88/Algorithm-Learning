package com.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 131. 分割回文串
 */
public class Solution131 {
    // List<List<String>> res = new ArrayList<>();
    // List<String> path = new ArrayList<>();
    //
    // public List<List<String>> partition(String s) {
    //     helper(s, 0);
    //     return res;
    // }
    //
    // private void helper(String s, int startIndex) {
    //     if (s == null || s.isEmpty()) return;
    //
    //     if (startIndex == s.length()) {
    //         res.add(new ArrayList<>(path));
    //     }
    //
    //     for (int i = startIndex; i < s.length(); i++) {
    //         String substr = s.substring(startIndex, i + 1);
    //         if (isPalindrome(substr)) {
    //             path.add(substr);
    //             helper(s, i + 1);
    //             path.remove(path.size() - 1);
    //         }
    //     }
    // }
    //
    // private boolean isPalindrome(String s) {
    //     int left = 0, right = s.length() - 1;
    //     while (left < right) {
    //         if (s.charAt(left++) != s.charAt(right--)) return false;
    //     }
    //     return true;
    // }

    List<List<String>> res = new ArrayList<>();
    List<String> path = new ArrayList<>();
    boolean[][] dp;

    public List<List<String>> partition(String s) {
        initDp(s);
        helper(s, 0);
        return res;
    }

    private void helper(String s, int startIndex) {
        if (s == null || s.isEmpty()) return;

        if (startIndex == s.length()) {
            res.add(new ArrayList<>(path));
        }

        for (int i = startIndex; i < s.length(); i++) {
            if (dp[startIndex][i]) {
                path.add(s.substring(startIndex, i + 1));
                helper(s, i + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    // 动态规划版本
    // dp[i][j] 字符串[i, j]是否为回文字符串
    private boolean initDp(String s) {
        dp = new boolean[s.length()][s.length()];

        // 单个字符的字符串一定是回文子串
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }

        for (int i = 1; i < s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i - j == 1) {
                        dp[j][i] = true;
                    } else if (dp[j + 1][i - 1]) {
                        dp[j][i] = true;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        List<List<String>> res = new Solution131().partition("efe");
        System.out.println(res);
    }
}
