package task6;

import java.util.Arrays;

/**
 * 28. 找出字符串中第一个匹配项的下标
 */
public class Solution28 {
    public static void main(String[] args) {
        Solution28 solution28 = new Solution28();
        int res = solution28.strStr("leetcode", "leeto");
        System.out.println(res);

        System.out.println(Arrays.toString(solution28.getNext("aabaaf")));
    }

    /*
        KMP
     */
    public int strStr(String haystack, String needle) {
        int[] next = getNext(needle);

        int j = -1;
        for (int i = 0; i < haystack.length(); i++) {
            while (j >= 0 && haystack.charAt(i) != needle.charAt(j + 1)) {
                j = next[j];
            }

            if (haystack.charAt(i) == needle.charAt(j + 1)) {
                j++;
            }

            if (j == needle.length() - 1) {
                return i - needle.length() + 1;
            }
        }

        return -1;
    }

    /*
        获得next数组
     */
    private int[] getNext(String str) {
        int[] next = new int[str.length()];

        int j = -1;
        next[0] = j;
        for (int i = 1; i < str.length(); i++) { // 注意i从1开始
            while (j >= 0 && str.charAt(i) != str.charAt(j + 1)) { // 前后缀不相同了
                j = next[j]; // 向前回退
            }
            if (str.charAt(i) == str.charAt(j + 1)) { // 找到相同的前后缀
                j++;
            }
            next[i] = j; // 将j（前缀的长度）赋给next[i]
        }

        return next;
    }

    /*
        暴力
     */
    public int strStr2(String haystack, String needle) {
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            int j;
            for (j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == needle.length()) {
                return i;
            }
        }
        return -1;
    }

    /*
        库函数
     */
    public int strStr1(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
