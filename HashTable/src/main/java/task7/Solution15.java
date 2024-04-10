package task7;

import java.util.*;

/**
 * 15. 三数之和
 */
public class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        int left, right, sum;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return res;
            }

            /*
             已经使用过了 nums[i]
             if (nums[i] == nums[i + 1]) 是错误的，因为三元组内的元素是可以重复的
             */
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            left = i + 1;
            right = nums.length - 1;

            while (left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    /*
                    nums[left] nums[right]去重
                     */
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    right--;
                    left++;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = new Solution15().threeSum(new int[]{-1,0,1,2,-1,-4});
        lists.forEach(System.out::println);
    }
}
