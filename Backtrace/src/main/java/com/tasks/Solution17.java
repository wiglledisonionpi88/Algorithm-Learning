package com.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 17. 电话号码的字母组合
 */
public class Solution17 {
    char[][] table = new char[][]{
            new char[]{},
            new char[]{},
            new char[]{'a', 'b', 'c'},
            new char[]{'d', 'e', 'f'},
            new char[]{'g', 'h', 'i'},
            new char[]{'j', 'k', 'l'},
            new char[]{'m', 'n', 'o'},
            new char[]{'p', 'q', 'r', 's'},
            new char[]{'t', 'u', 'v'},
            new char[]{'w', 'x', 'y', 'z'}
    };

    List<String> res = new ArrayList<>();
    StringBuilder path = new StringBuilder();

    public List<String> letterCombinations(String digits) {
        char[] arr = digits.toCharArray();
        helper(arr, 0);
        return res;
    }

    private void helper(char[] arr, int startIndex) {
        if (arr.length == 0) return;
        if (startIndex >= arr.length) {
            res.add(path.toString());
            return;
        }
        for (char ch : table[arr[startIndex] - '0']) {
            path.append(ch);
            helper(arr, startIndex + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }

    public static void main(String[] args) {
        List<String> res = new Solution17().letterCombinations("234");
        System.out.println(res);
    }

}
