package task1to40.task39;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 47. 全排列 II <br/>
 * 同枝可重复，同层不可
 */
public class Solution47 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private List<Integer> remain = new ArrayList<>();

    // public List<List<Integer>> permuteUnique(int[] nums) {
    //     for (int num : nums) remain.add(num);
    //     Collections.sort(remain);
    //     permuteHelper();
    //     return res;
    // }
    //
    // private void permuteHelper() {
    //     if (remain.isEmpty()) res.add(new ArrayList<>(path));
    //
    //     for (int i = 0; i < remain.size(); i++) {
    //         if (i > 0 && remain.get(i).equals(remain.get(i - 1))) {
    //             continue;
    //         }
    //         path.add(remain.remove(i));
    //
    //         permuteHelper();
    //
    //         remain.add(i, path.remove(path.size() - 1));
    //     }
    // }

    // -----

    boolean[] used;

    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        Arrays.sort(nums);
        permuteHelper(nums);
        return res;
    }

    private void permuteHelper(int[] nums) {
        if (path.size() == nums.length) res.add(new ArrayList<>(path));

        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }

            path.add(nums[i]);
            used[i] = true;
            permuteHelper(nums);
            used[i] = false;
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = new Solution47().permuteUnique(new int[]{1, 1, 2});
        System.out.println(res);
    }
}
