package task35;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. 组合总和 II <br/>
 * 同枝可重复，同层不可重复 <br/>
 * <img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20230310000918.png"/>
 * <img src="https://code-thinking-1253855093.file.myqcloud.com/pics/20230829193102.png"/>
 */
public class Solution40 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private Integer curSum = 0;
    private boolean[] used;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        used = new boolean[candidates.length];
        Arrays.sort(candidates);
        combinationSum2Helper(candidates, target, 0);
        return res;
    }

    private void combinationSum2Helper2(int[] candidates, int target, int start) {
        if (curSum > target) return;
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }

        // candidates[i] == candidates[i - 1] 说明之前碰到过这个元素
        // used[i - 1] == true，当前正在使用，说明同一树枝candidates[i - 1]使用过
        // used[i - 1] == false，当前不在使用，说明同一树层candidates[i - 1]使用过
        // 要对同一树层使用过的元素进行跳过
        // if (i > 0 && candidates[i] == candidates[i - 1] && used[i - 1] == false) {
        //     continue;
        // }

        for (int i = start; i < candidates.length; i++) {
            if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) {
                continue;
            }

            path.add(candidates[i]);
            curSum += candidates[i];
            used[i] = true;

            combinationSum2Helper(candidates, target, i + 1);

            path.remove(path.size() - 1);
            curSum -= candidates[i];
            used[i] = false;
        }
    }

    private void combinationSum2Helper(int[] candidates, int target, int start) {
        if (curSum > target) return;
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 大于 start，说明这次选择是同层的
        // candidates[i] == candidates[i - 1]，说明之前出现过该元素
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            path.add(candidates[i]);
            curSum += candidates[i];

            combinationSum2Helper(candidates, target, i + 1);

            path.remove(path.size() - 1);
            curSum -= candidates[i];
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = new Solution40()
                .combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);

        System.out.println("res = " + res);
    }
}












