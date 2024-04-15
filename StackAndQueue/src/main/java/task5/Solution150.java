package task5;

import java.util.ArrayDeque;

/**
 * 150. 逆波兰表达式求值
 */
public class Solution150 {
    public static void main(String[] args) {
        int res = new Solution150().evalRPN(new String[]{"4", "13", "5", "/", "+"});
        System.out.println(res);
    }

    public int evalRPN(String[] tokens) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            Integer a;
            Integer b;
            switch (token) {
                case "+":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b + a);
                    break;
                case "-":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b - a);
                    break;
                case "*":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b * a);
                    break;
                case "/":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b / a);
                    break;
                default:
                    stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
