package task61;

/**
 * 647. 回文子串
 */
public class Solution647 {
    public int countSubstrings1(String s) {
        char[] chars = s.toCharArray();
        // 布尔类型的dp[i][j]：表示区间范围[i,j] （注意是左闭右闭）的子串是否是回文子串，如果是dp[i][j]为true，否则为false。
        boolean[][] dp = new boolean[chars.length][chars.length];
        int res = 0;

        for (int i = chars.length - 1; i >= 0; i--) {
            for (int j = i; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    if (j - i <= 1) {
                        res++;
                        dp[i][j] = true;
                    } else if (dp[i + 1][j - 1]) {
                        res++;
                        dp[i][j] = true;
                    }
                }
            }
        }

        return res;
    }

    public int countSubstrings(String s) {
        int res = 0;
        char[] chars = s.toCharArray();

        // 一个中心点
        for (int i = 0; i < chars.length; i++) {
            int left = i;
            int right = i;
            while (left >= 0 && right < s.length() && chars[left] == chars[right]) {
                left--;
                right++;
                res++;
            }
        }

        // 二个中心点
        for (int i = 0; i < chars.length - 1; i++) {
            int left = i;
            int right = i + 1;
            while (left >= 0 && right < s.length() && chars[left] == chars[right]) {
                left--;
                right++;
                res++;
            }
        }

        return res;
    }


    public static void main(String[] args) {
        int res = new Solution647().countSubstrings("aaa");
        System.out.println(res);
    }
}
