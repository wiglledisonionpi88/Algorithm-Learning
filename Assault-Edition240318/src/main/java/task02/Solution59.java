package task02;

/**
 * 59. 螺旋矩阵 II
 */
public class Solution59 {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = left + (right - left) / 2;

        while (left <= right) {
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid  + 1;
            }
            mid = left + (right - left) / 2;
        }
        return -1;
    }
}
