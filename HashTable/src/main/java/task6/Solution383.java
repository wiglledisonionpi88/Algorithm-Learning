package task6;

/**
 * 383. 赎金信
 */
public class Solution383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) return false;
        int[] map = new int[26];
        for (char c : magazine.toCharArray()) {
            ++map[c - 'a'];
        }
        for (char c : ransomNote.toCharArray()) {
            if (map[c - 'a']-- == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean res = new Solution383().canConstruct("a", "b");
        System.out.println(res);
    }
}
