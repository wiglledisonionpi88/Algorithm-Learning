package task1to40.task07;

/**
 * 242. 有效的字母异位词
 */
public class Solution242 {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] map = new int[26];
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            map[sChars[i] - 'a']++;
            map[tChars[i] - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (map[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
