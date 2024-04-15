package daily.task1002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution1002 {
    public static void main(String[] args) {
        String[] strings = new String[]{ "bella","label","roller" };
        List<String> list = new Solution1002().commonChars(strings);
        System.out.println(list);
    }

    public List<String> commonChars(String[] words) {
        int[] set1 = new int[26];
        int[] set2 = new int[26];

        for (char ch : words[0].toCharArray()) {
            set1[ch - 'a']++;
        }

        for (int i = 1; i < words.length; i++) {
            Arrays.fill(set2, 0);

            for (char ch : words[i].toCharArray()) {
                set2[ch - 'a']++;
            }

            for (int j = 0; j < set1.length; j++) {
                set1[j] = Math.min(set1[j], set2[j]);
            }
        }
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < set1[i]; j++) {
                res.add("" + (char)('a' + i));
            }
        }
        return res;
    }
}
