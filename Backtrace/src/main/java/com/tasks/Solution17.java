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
    List<Integer> idxs = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        char[] arr = digits.toCharArray();
        helper(arr, 0);
        return res;
    }

    private void helper(char[] arr, int startIndex) {
        if (idxs.size() - 1 < startIndex) {
            idxs.add(0);
        }

        if (startIndex >= arr.length) {
            res.add(path.toString());
            return;
        }

        for (int i = startIndex; i < arr.length; i++) {
            path.append(table[idxs.get(i)]);
            idxs.set(i, idxs.get(i) + 1);
            helper(arr, i + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }


}
