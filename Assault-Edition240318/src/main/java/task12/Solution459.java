package task12;

/**
 * 459. 重复的子字符串
 */
public class Solution459 {
    public boolean repeatedSubstringPattern1(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }
    public boolean repeatedSubstringPattern2(String s) {
        int n = s.length();
        for (int i = 1; i * 2 <= n; ++i) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; ++j) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean repeatedSubstringPattern(String s) {
        char[] chars = s.toCharArray();
        for (int i = 1; i <= chars.length / 2; i++) {
            if (chars.length % i == 0) {
                boolean match = true;
                for (int j = i; j < chars.length; j++) {
                    if (chars[j] != chars[j - i]) {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
    }
}