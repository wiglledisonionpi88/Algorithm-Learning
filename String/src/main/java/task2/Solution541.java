package task2;

/**
 * 541. 反转字符串II
 */
public class Solution541 {
    public String reverseStr1(String s, int k) {
        char[] chars = s.toCharArray();
        boolean flag = true;
        for (int i = 0; i < s.length(); i += k) {
            if (flag) {
                reverse(chars, i, Math.min(s.length() - 1, i + k - 1));
            }
            flag = !flag;
        }
        return new String(chars);
    }

    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        // chars.length 执行速度快于 s.length()
        for (int i = 0; i < chars.length; i += 2 * k) {
            reverse(chars, i, Math.min(s.length() - 1, i + k - 1));
        }
        return new String(chars);
    }

    public void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char tmp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = tmp;
        }
    }

    public static void main(String[] args) {
        // 输入：s = "abcdefg", k = 2
        // 输出："bacdfeg"
        String s = new Solution541().reverseStr("abcdefg", 2);
        System.out.println(s);
    }
}
