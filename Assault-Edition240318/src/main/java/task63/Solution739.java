package task63;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * 739. 每日温度
 */
public class Solution739 {
    /**
     * 暴力 超时
     */
    public int[] dailyTemperatures1(int[] temperatures) {
        int[] res = new int[temperatures.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = i + 1; j < res.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    res[i] = j - i;
                    break;
                }
            }
        }
        return res;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] res = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                Integer index = stack.pop();
                res[index] = i - index;
            }
            stack.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] res = new Solution739().dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        System.out.println(Arrays.toString(res));
    }

}
