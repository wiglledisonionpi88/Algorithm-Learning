package task13;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 20. 有效的括号
 */
public class Solution20 {
    public boolean isValid1(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        char[] chars = s.toCharArray();
        if (chars.length % 2 == 1) return false;

        for (char c : chars) {
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

    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        char[] stack = new char[s.length()];
        int top = 0;
        if (chars.length % 2 == 1) return false;

        for (char c : chars) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack[top++] = c;
                    break;
                case ')':
                    if (top == 0 || !(stack[--top] == '(')) return false;
                    break;
                case ']':
                    if (top == 0 || !(stack[--top] == '[')) return false;
                    break;
                case '}':
                    if (top == 0 || !(stack[--top] == '{')) return false;
                    break;
            }
        }
        return top == 0;
    }
}
