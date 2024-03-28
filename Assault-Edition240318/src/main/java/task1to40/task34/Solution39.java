package task1to40.task34;

import java.util.ArrayList;
import java.util.List;

/**
 * 39. 组合总和
 */
public class Solution39 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private Integer curSum = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        combinationSumHelper(candidates, target, 0);
        return res;
    }

    /**
     * 原版
     */
    private void combinationSumHelper(int[] candidates, int target, int start) {
        if (curSum > target) return;    // 剪枝

        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            path.add(candidates[i]);
            curSum += candidates[i];

            combinationSumHelper(candidates, target, i);

            path.remove(path.size() - 1);
            curSum -= candidates[i];
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = new Solution39().combinationSum(new int[] {2, 3, 5}, 8);
        System.out.println("res = " + res);
    }
}
















