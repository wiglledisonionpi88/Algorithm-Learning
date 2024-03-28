package task1to40.task33;

import java.util.ArrayList;
import java.util.List;

/**
 * 216. 组合总和 III
 */
public class Solution216 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private int curSum = 0;

    public List<List<Integer>> combinationSum3(int k, int n) {
        combinationSum3Helper(k, n, 1);
        return res;
    }

    /**
     * 原版
     */
    private void combinationSum3Helper1(int k, int n, int start) {
        if (path.size() == k && curSum == n) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= 9; i++) {
            path.add(i);
            curSum += i;

            combinationSum3Helper(k, n, i + 1);

            path.remove(path.size() - 1);
            curSum -= i;
        }
    }

    /**
     * 剪枝优化
     */
    private void combinationSum3Helper2(int k, int n, int start) {
        if (path.size() == k && curSum == n) {
            res.add(new ArrayList<>(path));
        }

        // k - path.size() <= 9 - i + 1
        for (int i = start; curSum < n && i <= 10 + path.size() - k ; i++) {
            path.add(i);
            curSum += i;

            combinationSum3Helper(k, n, i + 1);

            path.remove(path.size() - 1);
            curSum -= i;
        }
    }


    /**
     * 剪枝优化
     */
    private void combinationSum3Helper(int k, int n, int start) {
        if (curSum > n) return;

        if (path.size() == k && curSum == n) {
            res.add(new ArrayList<>(path));
        }

        // k - path.size() <= 9 - i + 1
        for (int i = start; i <= 10 + path.size() - k ; i++) {
            path.add(i);
            curSum += i;

            combinationSum3Helper(k, n, i + 1);

            path.remove(path.size() - 1);
            curSum -= i;
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = new Solution216().combinationSum3(3, 7);
        System.out.println("res = " + res);
    }
}












