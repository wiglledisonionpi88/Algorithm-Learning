package task3;

import java.util.ArrayDeque;

/**
 * 20. 有效的括号
 */
public class Solution20 {
    public static void main(String[] args) {
        // 输入：s = "()[]{}"
        // 输出：true
        boolean valid = new Solution20().isValid("()[]{}");
        System.out.println(valid);
    }

    public boolean isValid(String s) {
        if ((s.length() & 1) == 1) return false;
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                case ')':
                    if (stack.isEmpty() || !stack.pop().equals('(')) return false;
                    break;
                case ']':
                    if (stack.isEmpty() || !stack.pop().equals('[')) return false;
                    break;
                case '}':
                    if (stack.isEmpty() || !stack.pop().equals('{')) return false;
                    break;
            }
        }
        return stack.isEmpty();
    }
}
