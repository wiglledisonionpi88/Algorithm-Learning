package com.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 77. 组合
 */
public class Solution77 {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        helper(n, k, 1);
        return res;
    }

    private void helper(int n, int k, int start) {
        if (k == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // for (int i = start; i <= n; i++) {
        for (int i = start;  n - i + 1 >= k; i++) {
            path.add(i);
            helper(n, k - 1, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> combine = new Solution77().combine(4, 2);
        System.out.println(combine);
    }
}
