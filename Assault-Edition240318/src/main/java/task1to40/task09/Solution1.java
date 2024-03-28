package task1to40.task09;

import java.util.HashMap;

/**
 * 1. 两数之和
 */
public class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            if (index != null) {
                return new int[] {i, index};
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }
}
