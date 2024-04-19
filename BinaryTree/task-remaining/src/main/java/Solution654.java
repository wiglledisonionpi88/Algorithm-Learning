import java.util.Arrays;

public class Solution654 {
    // public TreeNode constructMaximumBinaryTree(int[] nums) {
    //     if (nums.length == 0) return null;
    //     int idx = getIndexOfMaxValue(nums);
    //     return new TreeNode(nums[idx],
    //             constructMaximumBinaryTree(Arrays.copyOfRange(nums, 0, idx)),
    //             constructMaximumBinaryTree(Arrays.copyOfRange(nums, idx + 1, nums.length))
    //         );
    // }
    //
    // private int getIndexOfMaxValue(int[] nums) {
    //     int res = 0;
    //     for (int i = 1; i < nums.length; i++) {
    //         if (nums[res] < nums[i]) {
    //             res = i;
    //         }
    //     }
    //     return res;
    // }

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length);
    }

    private TreeNode helper(int[] nums, int start, int end) {
        if (start >= end) return null;

        int idx = getIndexOfMaxValue(nums, start, end);
        return new TreeNode(
                nums[idx],
                helper(nums, start, idx),
                helper(nums, idx + 1, end)
        );
    }

    private int getIndexOfMaxValue(int[] nums, int start, int end) {
        int res = start;
        for (int i = start + 1; i < end; i++) {
            if (nums[res] < nums[i]) {
                res = i;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new Solution654().constructMaximumBinaryTree(new int[]{3, 2, 1, 6, 0, 5});
        System.out.println(treeNode);
    }
}
