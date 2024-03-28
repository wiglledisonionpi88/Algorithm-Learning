package task1to40.task14;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 150. 逆波兰表达式求值
 */
public class Solution130 {
    public int evalRPN(String[] tokens) {
        Deque<Integer> deque = new ArrayDeque<>();
        int n1, n2;
        for (String token : tokens) {
            switch (token) {
                case "+":
                    n2 = deque.pop();
                    n1 = deque.pop();
                    deque.push(n1 + n2);
                    break;
                case "-":
                    n2 = deque.pop();
                    n1 = deque.pop();
                    deque.push(n1 - n2);
                    break;
                case "*":
                    n2 = deque.pop();
                    n1 = deque.pop();
                    deque.push(n1 * n2);
                    break;
                case "/":
                    n2 = deque.pop();
                    n1 = deque.pop();
                    deque.push(n1 / n2);
                    break;
                default:
                    deque.push(Integer.parseInt(token));
            }
        }
        return deque.pop();
    }
}
