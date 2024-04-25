package com.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 216. 组合总和 III
 */
public class Solution216 {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path =  new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        helper(k, n, 1);
        return res;
    }

    private void helper(int k, int n, int start) {
        if (k == 0 && n == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; 9 - i + 1 >= k && n > 0; i++) {
            path.add(i);
            helper(k - 1, n - i, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
