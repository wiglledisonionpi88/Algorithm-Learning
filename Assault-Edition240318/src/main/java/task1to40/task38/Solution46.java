package task1to40.task38;

import java.util.ArrayList;
import java.util.List;

/**
 * 46. 全排列
 */
public class Solution46 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    private List<Integer> remain = new ArrayList<>();

    // public List<List<Integer>> permute(int[] nums) {
    //     for (int num : nums) remain.add(num);
    //     permuteHelper();
    //     return res;
    // }
    //
    // private void permuteHelper() {
    //     if (remain.isEmpty()) res.add(new ArrayList<>(path));
    //
    //     for (int i = 0; i < remain.size(); i++) {
    //         path.add(remain.remove(i));
    //
    //         permuteHelper();
    //
    //         remain.add(i, path.remove(path.size() - 1));
    //     }
    // }


    // -----

    // public List<List<Integer>> permute(int[] nums) {
    //     permuteHelper(nums);
    //     return res;
    // }
    //
    // private void permuteHelper(int[] nums) {
    //     if (path.size() == nums.length) res.add(new ArrayList<>(path));
    //
    //     for (int i = 0; i < nums.length; i++) {
    //         // nums[i] 不重复
    //         if (path.contains(nums[i])) {
    //             continue;
    //         }
    //
    //         path.add(nums[i]);
    //         permuteHelper(nums);
    //         path.remove(path.size() - 1);
    //     }
    // }

    boolean[] used;
    public List<List<Integer>> permute(int[] nums) {
        used = new boolean[nums.length];
        permuteHelper(nums);
        return res;
    }

    private void permuteHelper(int[] nums) {
        if (path.size() == nums.length) res.add(new ArrayList<>(path));

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
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
        List<List<Integer>> res = new Solution46().permute(new int[]{1, 2, 3});
        System.out.println(res);
    }
}
