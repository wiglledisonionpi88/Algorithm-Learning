package com.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. 组合总和 II
 */
public class Solution40 {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        helper(candidates, target, 0);
        return res;
    }

    private void helper(int[] candidates, int target, int startIndex) {
        if (candidates == null || candidates.length == 0) {
            return;
        }

        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if (target < candidates[i]) return;
            if (i > startIndex && candidates[i] == candidates[i - 1]) continue;

            path.add(candidates[i]);
            helper(candidates, target - candidates[i], i + 1);
            path.remove(path.size() - 1);
        }
    }
}
