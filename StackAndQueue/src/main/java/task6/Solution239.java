package task6;

import java.util.*;

/**
 * 239. 滑动窗口最大值
 */
public class Solution239 {
    public static void main(String[] args) {
        int[] nums = new int[]{ 1,3,1,2,0,5 };
        int[] res = new Solution239().maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(res));
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        MyQueue queue = new MyQueue();

        int[] res = new int[nums.length - k + 1];
        int index = 0;

        for (int i = 0; i < k; i++) {
            queue.offer(nums[i]);
        }
        res[index++] = queue.peek();

        for (int i = k; i < nums.length; i++) {
            queue.offer(nums[i]);
            queue.poll(nums[i - k]);
            res[index++] = queue.peek();
        }

        return res;
    }

    static class MyQueue {
        private final Deque<Integer> queue = new ArrayDeque<>();

        public boolean offer(Integer integer) {
            while (!queue.isEmpty() && queue.peekLast() < integer) {
                queue.pollLast();
            }
            queue.offer(integer);
            return true;
        }

        public void poll(Integer integer) {
            if (queue.peek().equals(integer)) {
                queue.poll();
            }
        }

        public int peek() {
            return queue.peek();
        }
    }
}
