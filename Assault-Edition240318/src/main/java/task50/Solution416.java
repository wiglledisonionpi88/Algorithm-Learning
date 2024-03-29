package task50;

/**
 * 416. 分割等和子集
 */
public class Solution416 {
    private int curSum = 0;
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 == 1) return false;
        int target = sum / 2;

        // 回溯
        return canPartitionHelper(nums, target, 0);
    }

    private boolean canPartitionHelper(int[] nums, int target, int start) {
        if (target == curSum) return true;

        for (int i = start; i < nums.length; i++) {
            curSum += nums[i];

            if (canPartitionHelper(nums, target, i + 1)) {
                // 传递结果 true
                return true;
            }

            curSum -= nums[i];
        }
        return false;
    }

    public boolean canPartition1(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 == 1) return false;
        int target = sum / 2;

        /*
            背包
            dp[i]：在集合元素和不超过i的情况下，集合的最大和（dp[i] == i，即 dp[target] == target）
            1 <= nums.length <= 200
            1 <= nums[i] <= 100
            10000 + 1
         */
        int[] dp = new int[target + 1];
        for (int num : nums) {
            for (int j = dp.length - 1; j >= num; j--) {
                dp[j] = Math.max(dp[j], dp[j - num] + num);
                if (j == target && dp[target] == target) return true;
            }
        }
        return dp[target] == target;
    }

    public static void main(String[] args) {
        // 输入：nums = [1,5,11,5]
        // 输出：true
        // 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
        boolean res1 = new Solution416().canPartition(new int[]{1, 5, 11, 5});
        System.out.println(res1);

        // 输入：nums = [1,2,3,5]
        // 输出：false
        // 解释：数组不能分割成两个元素和相等的子集。
        boolean res2 = new Solution416().canPartition(new int[]{1, 2, 3, 5});
        System.out.println(res2);

        // 输入：nums = [100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,97]
        // 输出：false
        // 解释：数组不能分割成两个元素和相等的子集。
        boolean res3 = new Solution416().canPartition(new int[]{99,97,100,100});
        System.out.println(res3);
    }
}
