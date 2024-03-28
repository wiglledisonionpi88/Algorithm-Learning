package task1to40.task36;

import java.util.ArrayList;
import java.util.List;

/**
 * 78. 子集
 */
public class Solution78 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> subset = new ArrayList<>();

    // public List<List<Integer>> subsets1(int[] nums) {
    //     for (int i = 0; i <= nums.length; i++) {
    //         subsetsHelper(nums, i, 0);
    //     }
    //
    //     return res;
    // }
    //
    // private void subsetsHelper1(int[] nums, int size, int start) {
    //     if (subset.size() == size) {
    //         res.add(new ArrayList<>(subset));
    //     }
    //
    //     for (int i = start; i < nums.length; i++) {
    //         subset.add(nums[i]);
    //         subsetsHelper(nums, size, i + 1);
    //         subset.remove(subset.size() - 1);
    //     }
    // }
    //
    // /**
    //  * 剪枝
    //  */
    // private void subsetsHelper2(int[] nums, int size, int start) {
    //     if (subset.size() == size) {
    //         res.add(new ArrayList<>(subset));
    //     }
    //
    //     // nums.length - i >= size - subset.size()
    //     // i <= nums.length + subset.size() - size
    //     for (int i = start; i <= nums.length + subset.size() - size && i < nums.length; i++) {
    //         subset.add(nums[i]);
    //         subsetsHelper(nums, size, i + 1);
    //         subset.remove(subset.size() - 1);
    //     }
    // }

    // -----

    public List<List<Integer>> subsets(int[] nums) {
        subsetsHelper(nums, 0);
        return res;
    }

    private void subsetsHelper(int[] nums, int start) {
        // 遍历的所有过程就是子集
        res.add(new ArrayList<>(subset));

        for (int i = start; i < nums.length; i++) {
            subset.add(nums[i]);
            subsetsHelper(nums, i + 1);
            subset.remove(subset.size() - 1);
        }
    }


    public static void main(String[] args) {
        List<List<Integer>> subsets = new Solution78().subsets(new int[]{1, 2, 3});
        subsets.forEach(System.out::println);
    }
}
