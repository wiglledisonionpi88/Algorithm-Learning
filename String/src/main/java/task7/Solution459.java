package task7;

/**
 * 459. 重复的子字符串
 */
public class Solution459 {
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }
}
