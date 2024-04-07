package task4;

/**
 * 209. 长度最小的子数组
 */
public class Solution209 {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int res = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                res = Math.min(res, right - left + 1);
                sum -= nums[left++];
            }
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public static void main(String[] args) {
        // 输入：target = 7, nums = [2,3,1,2,4,3]
        // 输出：2
        // 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
        int res = new Solution209().minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        System.out.println(res);
    }
}
