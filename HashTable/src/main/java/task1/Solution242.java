package task1;

/**
 * 242. 有效的字母异位词
 */
public class Solution242 {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] map = new int[26];
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        for (char c : sArr) {
            ++map[c - 'a'];
        }

        for (char c : tArr) {
            --map[c - 'a'];
        }

        for (int i : map) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";

        boolean res = new Solution242().isAnagram(s, t);
        System.out.println(res);
    }
}
