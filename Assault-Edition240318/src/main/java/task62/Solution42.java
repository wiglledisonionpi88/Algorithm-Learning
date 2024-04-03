package task62;

import java.util.ArrayDeque;

/**
 * 42. 接雨水
 */
public class Solution42 {
    public int trap1(int[] height) {
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];

        leftMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[rightMax.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int res = 0;
        for (int i = 1; i < height.length - 1; i++) {
            int h = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (h > 0) res += h;
        }

        return res;
    }

    public int trap(int[] height) {
        int res = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < height.length; i++) {
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                if (height[i] == height[stack.peek()]) {
                    stack.pop();
                    stack.push(i);
                } else if (height[i] < height[stack.peek()]) {
                    stack.push(i);
                } else {
                    while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                        Integer index = stack.pop();
                        if (!stack.isEmpty()) {
                            Integer left = stack.peek();
                            // 高度
                            int h = Math.min(height[left], height[i]) - height[index];
                            res += (i - left - 1) * h;
                        }
                    }
                    stack.push(i);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // res = 9
        int res = new Solution42().trap(new int[]{4,2,0,3,2,5});
        System.out.println(res);
    }
}
