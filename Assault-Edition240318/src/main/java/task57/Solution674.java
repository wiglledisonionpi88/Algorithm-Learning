package task57;

/**
 * 674. 最长连续递增序列
 */
public class Solution674 {
    public int findLengthOfLCIS1(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }

            if (res < dp[i]) res = dp[i];
        }
        return res;
    }

    public int findLengthOfLCIS(int[] nums) {
        int res = 1;
        int cur = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                cur++;
            } else {
                cur = 1;
            }

            if (res < cur) res = cur;
        }
        return res;
    }

    public static void main(String[] args) {
        // 输入：nums = [1,3,5,4,7]
        // 输出：3
        // 解释：最长连续递增序列是 [1,3,5], 长度为3。

        int res = new Solution674().findLengthOfLCIS(new int[]{1, 3, 5, 4, 7});
        System.out.println(res);
    }
}
