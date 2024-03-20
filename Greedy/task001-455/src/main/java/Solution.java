import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                1,
                new Solution().findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1}));

        Assertions.assertEquals(
                2,
                new Solution().findContentChildren2(new int[]{10,9,8,7}, new int[]{5,6,7,8}));
    }
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int res = 0;
        for (int gIndex = 0, sIndex = 0; gIndex < g.length && sIndex < s.length;) {
            if (s[sIndex] >= g[gIndex]) {
                gIndex++;
                sIndex++;
                res++;
            } else {
                sIndex++;
            }
        }
        return res;
    }

    public int findContentChildren2(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int gIndex = 0;
        for (int i : s) {
            if (gIndex < g.length && g[gIndex] <= i) {
                gIndex++;
            }
        }
        return gIndex;
    }
}