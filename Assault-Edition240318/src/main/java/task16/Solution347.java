package task16;

import java.util.*;

/**
 * 347.前K个⾼频元素
 */
public class Solution347 {
    //    输入: nums = [1,1,1,2,2,3], k = 2
//    输出: [1,2]
    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 2, 2, 3};
        int k = 2;
        int[] ints = new Solution347().topKFrequent(nums, k);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * Map统计，优先队列排序
     */
    public int[] topKFrequent1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        // 优先级队列  大堆
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((pair1, pair2) -> pair2[1] - pair1[1]);
        map.forEach((num, count) -> priorityQueue.add(new int[] {num, count}));

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = priorityQueue.poll()[0];
        }

        return res;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        // 优先级队列  小堆
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair[1]));
        map.forEach((num, count) -> {
            if (priorityQueue.size() < k) {
                priorityQueue.add(new int[] {num, count});
            } else if (count > priorityQueue.peek()[1]) {
                priorityQueue.poll();
                priorityQueue.add(new int[] {num, count});
            } else {
                // do nothing
            }
        });

        int[] res = new int[k];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = priorityQueue.poll()[0];
        }

        return res;
    }
}