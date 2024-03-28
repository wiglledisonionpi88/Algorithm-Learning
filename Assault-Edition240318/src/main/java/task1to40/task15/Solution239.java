package task1to40.task15;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 239.滑动窗⼝最⼤值
 */
public class Solution239 {
    // oot
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length - k + 1; i++) {
            res[i] = findMax(nums, i, i + k - 1);
        }
        return res;
    }
    private int findMax(int[] nums, int start, int end) {
        int max = nums[start];
        for (int i = start + 1; i <= end; i++) {
            if (max < nums[i]) max = nums[i];
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] res = new Solution239().maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(res));
//        int[] nums = {7,2,4};
//        int[] res = new Solution239().maxSlidingWindow(nums, 2);
//        System.out.println(Arrays.toString(res));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) return nums;

        int[] res = new int[nums.length - k + 1];
        MyQueue myQueue = new MyQueue();
        for (int i = 0; i < k; i++) {
            myQueue.offer(nums[i]);
        }
//        res[0] = myQueue.poll(nums[0]);
//
//        for (int i = 1; i < nums.length - k + 1; i++) {
//            myQueue.offer(nums[i + k - 1]);
//            Integer max = myQueue.poll(nums[i]);
//            res[i] = max;
//        }
//        return res;

        for (int i = 0; i < nums.length - k; i++) {
            res[i] = myQueue.poll(nums[i]);
            myQueue.offer(nums[i + k]);
        }
        res[res.length - 1] = myQueue.poll(0);
        return res;
    }
}

class MyQueue {
    private final Deque<Integer> deque = new ArrayDeque<>();

    /**
     * “如果一个选手比你小还比你强，你就可以退役了。”    ——单调队列的原理
     * 移除前面比当前值小的所有元素
     * @param val 要添加的值
     */
    public void offer(int val) {
        while (!deque.isEmpty() && deque.peekLast() < val) {
            deque.pollLast();
        }
        deque.offer(val);
    }

    /**
     * 如果等于当前单调队列的最大值，弹出最大值(poll)，否则取出最大值(peek)
     * @param val 滑动窗口划走的值
     * @return 单调队列的最大值
     */
    public Integer poll(int val) {
        Integer max = deque.peek();

        // 滑动窗口划走的值 等于 当前单调队列的最大值
        if (max != null && max == val) {
            deque.poll();
        }

        return max;
    }
}
