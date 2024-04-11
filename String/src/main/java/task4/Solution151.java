package task4;

import java.util.ArrayList;

/**
 * 151. 反转字符串中的单词
 */
public class Solution151 {

    public static void main(String[] args) {
        // 输入：s = "the sky is blue"
        // 输出："blue is sky the"
        String res = new Solution151().reverseWords("the sky is blue");
        System.out.println(res);
    }

    public String reverseWords2(String s) {
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

    public String reverseWords(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = s.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                stringBuilder.insert(0, ' ');
                offset = 0;
                while (i + 1 < chars.length && chars[i + 1] == ' ') i++;
            } else {
                stringBuilder.insert(offset++, chars[i]);
            }
        }
        if (stringBuilder.charAt(0) == ' ')
            stringBuilder.deleteCharAt(0);
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }


    public String reverseWords1(String s) {
        return String.join(" ", reverse(s.trim().split("\\s+")));
    }

    private String[] reverse(String[] strings) {
        int left = 0;
        int right = strings.length - 1;
        while (left < right) {
            String tmp = strings[left];
            strings[left++] = strings[right];
            strings[right--] = tmp;
        }
        return strings;
    }
}
