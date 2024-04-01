package task60;

/**
 * 1035. 不相交的线 <br/>
 * 最长公共子序列
 */
public class Solution1035 {

    /**
     * 二维数组
     */
    public int maxUncrossedLines1(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        int res = 0;

        for (int i = 1; i < nums1.length + 1; i++) {
            for (int j = 1; j < nums2.length + 1; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }

                if (dp[i][j] > res) res = dp[i][j];
            }
        }

        return res;
    }

    /**
     * 一维数组
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int[] dp = new int[nums2.length + 1];
        int res = 0;

        for (int i = 0; i < nums1.length; i++) {
            // 左上
            int leftTop = dp[0];
            for (int j = 1; j < nums2.length + 1; j++) {
                int cur = dp[j];
                if (nums1[i] == nums2[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    // 左 和 上
                    dp[j] = Math.max(dp[j - 1], dp[j]);
                }
                leftTop = cur;
                if (dp[j] > res) res = dp[j];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        // 输入：nums1 = [1,4,2], nums2 = [1,2,4]
        // 输出：2

        int res = new Solution1035().maxUncrossedLines(new int[]{1, 4, 2}, new int[]{1, 2, 4});
        System.out.println(res);
    }
}
