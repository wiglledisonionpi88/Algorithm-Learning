package com.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 39. 组合总和
 */
public class Solution39 {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
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
            if (i > 0 && candidates[i] == candidates[i - 1]) continue;
            if (target < candidates[i]) return;

            path.add(candidates[i]);
            helper(candidates, target - candidates[i], i);
            path.remove(path.size() - 1);
        }
    }

    // public List<List<Integer>> combinationSum(int[] candidates, int target) {
    //     List<List<Integer>> res = new ArrayList<>();
    //     List<Integer> path = new ArrayList<>();
    //     Arrays.sort(candidates);
    //     helper(candidates, target, 0, path, res);
    //     return res;
    // }
    //
    // private void helper(int[] candidates, int target, int startIndex, List<Integer> path, List<List<Integer>> res) {
    //     if (candidates == null || candidates.length == 0) {
    //         return;
    //     }
    //     if (target == 0) {
    //         res.add(new ArrayList<>(path));
    //         return;
    //     }
    //     for (int i = startIndex; target > 0 && i < candidates.length; i++) {
    //         if (i > 0 && candidates[i] == candidates[i - 1]) continue;
    //
    //         path.add(candidates[i]);
    //         helper(candidates, target - candidates[i], i, path, res);
    //         path.remove(path.size() - 1);
    //     }
    // }
}
