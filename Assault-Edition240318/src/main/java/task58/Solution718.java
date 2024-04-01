package task58;

/**
 * 718. 最长重复子数组
 */
public class Solution718 {
    public int findLength1(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length][nums2.length];
        int res = 0;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if ((i == 0 || j == 0) && nums1[i] == nums2[j]) {
                    dp[i][j] = 1;
                } else if (nums1[i] == nums2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }

                if (dp[i][j] > res) res = dp[i][j];
            }
        }
        return res;
    }

    public int findLength2(int[] nums1, int[] nums2) {
        int[] dp = new int[nums2.length];
        int res = 0;

        for (int i = 0; i < nums1.length; i++) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                if (i == 0 || j == 0) {
                    if (nums1[i] == nums2[j]) dp[j] = 1;
                    else dp[j] = 0;
                } else if (nums1[i] == nums2[j]) {
                    dp[j] = dp[j - 1] + 1;
                } else {
                    dp[j] = 0;
                }

                if (dp[j] > res) res = dp[j];
            }
        }
        return res;
    }

    public int findLength(int[] nums1, int[] nums2) {
        int[] dp = new int[nums2.length];
        int res = 0;

        for (int i = 0; i < nums1.length; i++) {
            for (int j = nums2.length - 1; j >= 0; j--) {
                if (nums1[i] == nums2[j]) {
                    if (j == 0) {
                        dp[j] = 1;
                    } else {
                        dp[j] = dp[j - 1] + 1;
                    }
                } else {
                    dp[j] = 0;
                }

                if (dp[j] > res) res = dp[j];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // // 输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
        // // 输出：3
        // // 解释：长度最长的公共子数组是 [3,2,1] 。
        // int res = new Solution718().findLength(new int[]{1, 2, 3, 2, 1}, new int[]{3, 2, 1, 4, 7});
        // System.out.println(res);

        int res = new Solution718().findLength(new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 0, 1, 1});
        System.out.println(res);
    }
}
