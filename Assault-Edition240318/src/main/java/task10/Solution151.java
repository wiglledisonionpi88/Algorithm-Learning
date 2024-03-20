package task10;

import java.util.ArrayList;
import java.util.Collections;

public class Solution151 {
    public static void main(String[] args) {
        String s = "  hello world  ";
        System.out.println(new Solution151().reverseWords(s));
    }
    public String reverseWords1(String s) {
        String[] strings = s.trim().split("\\s");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            stringBuilder.append(strings[i]).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        int i = chars.length - 1;
        int start, end;

        while (true) {
            while (i >= 0 && chars[i] == ' ') i--;
            if (i < 0) break;

            start = i + 1;
            while (i >= 0 && chars[i] != ' ') i--;
            end = i + 1;
            list.add(new String(chars, end, start - end));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
