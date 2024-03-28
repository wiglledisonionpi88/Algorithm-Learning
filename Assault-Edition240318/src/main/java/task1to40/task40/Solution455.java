package task1to40.task40;

import java.util.Arrays;

/**
 * 455. 分发饼干
 */
public class Solution455 {
    public static void main(String[] args) {
        // 输入: g = [1,2,3], s = [1,1]
        // 输出: 1
        int res = new Solution455().findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1});
        System.out.println(res);
    }
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0, j = 0;
        int res = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                i++;
                j++;
                res++;
            } else {
                j++;
            }
        }
        return res;
    }
}
