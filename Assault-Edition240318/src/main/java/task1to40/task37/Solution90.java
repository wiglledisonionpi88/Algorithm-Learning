package task1to40.task37;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. 子集 II
 * 同枝可重复，同层不可
 */
public class Solution90 {
    public static void main(String[] args) {
        List<List<Integer>> res = new Solution90().subsetsWithDup(new int[]{1, 2, 2});
        System.out.println(res);
        // [[],[1],[1,2],[1,2,2],[2],[2,2]]
    }

    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        subsetsWithDupHelper(nums, 0);
        return res;
    }

    private void subsetsWithDupHelper(int[] nums, int start) {
        res.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            path.add(nums[i]);
            subsetsWithDupHelper(nums, i + 1);
            path.remove(path.size() - 1);
        }
    }
}
