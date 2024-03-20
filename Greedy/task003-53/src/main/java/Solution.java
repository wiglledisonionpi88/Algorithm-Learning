import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
//        Assertions.assertEquals(
//                6,
//                new Solution().maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4})
//        );

//        Assertions.assertEquals(
//                6,
//                new Solution().maxSubArrayDp(new int[]{-2,1,-3,4,-1,2,1,-5,4})
//        );

        Assertions.assertEquals(
                6,
                new Solution().maxSubArrayDp2(new int[]{-2,1,-3,4,-1,2,1,-5,4})
        );

        Assertions.assertEquals(
                -1,
                new Solution().maxSubArrayDp2(new int[]{-1,-2})
        );

        Assertions.assertEquals(
                6,
                new Solution().maxSubArrayDp2(new int[]{-2,1,-3,4,-1,2,1,-5,4})
        );
    }

    public int maxSubArray(int[] nums) {
        int count = 0;
        int res = Integer.MIN_VALUE;

        for (int num : nums) {
            count += num;
            if (count > res) res = count;

            // 负数（和）不可能会被包含，重新计算
            if (count < 0) count = 0;
        }
        return res;
    }


    public int maxSubArrayDp(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        // 动态规划版本
        // dp[i]（包含）和前面元素组成的数组的最大子序列和
        int[] dp = new int[nums.length];
        int res = nums[0];

        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            if (res < dp[i]) {
                res = dp[i];
            }
        }

        return res;
    }

    public int maxSubArrayDp2(int[] nums) {
        // 动态规划版本
        // dp 第i个元素（必定包含）和前面组成的数组的最大子序列和
        int dp = nums[0];
        int res = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dp = Math.max(dp + nums[i], nums[i]);
            if (res < dp) res = dp;
        }

        return res;
    }
}