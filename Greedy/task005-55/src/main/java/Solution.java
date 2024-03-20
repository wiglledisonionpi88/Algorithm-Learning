import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertTrue(new Solution().canJump(new int[]{2,3,1,1,4}));
        Assertions.assertFalse(new Solution().canJump(new int[]{3,2,1,0,4}));
        Assertions.assertTrue(new Solution().canJump(new int[]{1,2}));
    }

    public boolean canJump(int[] nums) {
        if (nums.length <= 1) return true;

        // max 最大覆盖到的下标
        int max = nums[0];
        for (int i = 1; i <= max; i++) {
            max = Math.max(max, nums[i] + i);
            if (max >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }
}