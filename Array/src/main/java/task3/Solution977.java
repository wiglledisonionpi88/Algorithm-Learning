package task3;

import java.util.Arrays;

/**
 * 977. 有序数组的平方
 */
public class Solution977 {
    /**
     * 正序添加
     */
    public int[] sortedSquares1(int[] nums) {
        if (nums.length == 1) {
            return new int[]{nums[0] * nums[0]};
        }

        int start = 0;
        while (start < nums.length && nums[start] < 0) {
            start++;
        }

        if (start > 0 && start < nums.length && nums[start - 1] + nums[start] > 0) {
            start--;
        }

        int left, right;
        if (start == 0) {
            left = -1;
            right = 0;
        } else if (start == nums.length) {
            left = nums.length - 1;
            right = nums.length;
        } else {
            left = start;
            right = start + 1;
        }

        int index = 0;
        int[] res = new int[nums.length];
        while (left >= 0 || right <= nums.length - 1) {
            if (left < 0) {
                res[index++] = nums[right] * nums[right];
                right++;
            } else if (right > nums.length - 1) {
                res[index++] = nums[left] * nums[left];
                left--;
            } else {
                int l = nums[left] * nums[left];
                int r = nums[right] * nums[right];
                if (l < r) {
                    res[index++] = l;
                    left--;
                } else {
                    res[index++] = r;
                    right++;
                }
            }
        }
        return res;
    }

    /**
     * 逆序添加
     */
    public int[] sortedSquares(int[] nums) {
        int[] res = new int[nums.length];
        int index = nums.length - 1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int l = nums[left] * nums[left];
            int r = nums[right] * nums[right];
            if (l < r) {
                res[index--] = r;
                right--;
            } else {
                res[index--] = l;
                left++;
            }
        }
        return res;
    }

        public static void main(String[] args) {
        int[] res = new Solution977().sortedSquares(new int[]{-1,2,2});
        System.out.println(Arrays.toString(res));
    }
}
