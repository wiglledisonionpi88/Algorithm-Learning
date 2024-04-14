package task7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 347. 前 K 个高频元素
 * Map计数 + priorityQueue排序
 */
public class Solution347 {
    public static void main(String[] args) {
        // 输入: nums = [1,1,1,2,2,3], k = 2
        // 输出: [1,2]
        int[] res = new Solution347().topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        System.out.println(Arrays.toString(res));
    }

    public int[] topKFrequent1(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // priorityQueue 默认为小顶堆实现，交换比较次序改为大顶堆
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(k, (a, b) -> b[1] - a[1]);
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        map.forEach((key, value) -> priorityQueue.offer(new int[]{key, value}));

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            res[i] = priorityQueue.poll()[0];
        }
        return res;
    }

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // priorityQueue 小顶堆实现
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(k, Comparator.comparingInt(arr -> arr[1]));
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }

        map.forEach((key, value) -> priorityQueue.offer(new int[]{key, value}));

        while (priorityQueue.size() > k) {
            priorityQueue.poll();
        }

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            res[i] = priorityQueue.poll()[0];
        }
        return res;
    }

}
