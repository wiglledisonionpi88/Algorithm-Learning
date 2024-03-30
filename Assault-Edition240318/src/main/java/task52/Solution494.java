package task52;

/**
 * 494. 目标和
 */
public class Solution494 {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (target > sum || target < -sum) return 0;
        /*
            plusSum + subSum = sum
            plusSum - subSum = target
            plusSum = (sum + target) / 2
         */
        if ((sum + target) % 2 == 1) return 0;
        int plusSum = (sum + target) / 2;

        int[] dp = new int[plusSum + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = plusSum; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[plusSum];
    }

    public static void main(String[] args) {
        // // 输入：nums = [1,1,1,1,1], target = 3
        // // 输出：5
        // int res = new Solution494().findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3);
        // System.out.println(res);

        int res = new Solution494().findTargetSumWays(new int[]{7, 9, 3, 8, 0, 2, 4, 8, 3, 9}, 0);
        System.out.println(res);
    }
}
