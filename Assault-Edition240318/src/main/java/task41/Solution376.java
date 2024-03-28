package task41;

/**
 * 376. 摆动序列
 */
public class Solution376 {
    public int wiggleMaxLength(int[] nums) {
        if (nums.length == 0) return 1;

        int res = 1;
        int preDiff = 0;
        int curDiff;
        for (int i = 1; i < nums.length; i++) {
            curDiff = nums[i] - nums[i - 1];
            if ((preDiff >= 0 && curDiff < 0) || (preDiff <= 0 && curDiff > 0)) {
                res++;
                preDiff = curDiff;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // 输入：nums = [1,7,4,9,2,5]
        // 输出：6
        int res = new Solution376().wiggleMaxLength(new int[]{1, 7, 4, 9, 2, 5});
        System.out.println(res);
    }
}
