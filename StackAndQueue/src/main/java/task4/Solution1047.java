package task4;

import java.util.ArrayDeque;

/**
 * 1047. 删除字符串中的所有相邻重复项
 */
public class Solution1047 {
    public static void main(String[] args) {
        String s = new Solution1047().removeDuplicates("abbaca");
        System.out.println(s);
    }

    public String removeDuplicates(String s) {
        int top = 1;
        char[] chars = s.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (top == 0) {
                chars[top++] = chars[i];
            } else if (chars[top - 1] == chars[i]) {
                top--;
            } else {
                chars[top++] = chars[i];
            }
        }
        return new String(chars, 0, top);
    }

    public String removeDuplicates1(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (stack.isEmpty()) {
                stack.push(s.charAt(i));
            } else if (stack.peek().equals(s.charAt(i))) {
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.insert(0, stack.pop());
        }
        return stringBuilder.toString();
    }
}
