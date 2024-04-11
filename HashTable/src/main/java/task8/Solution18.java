package task8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 四数之和
 */
public class Solution18 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            // 去重 a
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < nums.length; j++) {
                // 剪枝
                if (nums[i] + nums[j] >= target && nums[i] > 0) {
                    return res;
                }

                // 去重 b
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int left = j + 1;
                int right = nums.length - 1;
                int sum;
                while (left < right) {
                    sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum > target) {
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        // 相等
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 去重 c
                        while (left < right && nums[left] == nums[left + 1]) left++;

                        // 去重 d
                        while (left < right && nums[right] == nums[right - 1]) right--;

                        left++;
                        right--;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // 输入：nums = [1,0,-1,0,-2,2], target = 0
        // 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
        // List<List<Integer>> res = new Solution18().fourSum(new int[]{
        //         1, 0, -1, 0, -2, 2
        // }, 0);
        // res.forEach(System.out::println);

        List<List<Integer>> res = new Solution18().fourSum(new int[]{
                -2, -1, -1, 1, 1, 2, 2
        }, 0);
        res.forEach(System.out::println);
    }
}
