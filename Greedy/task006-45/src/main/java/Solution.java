import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                2,
                new Solution().jump(new int[]{2,3,1,1,4})
        );

        Assertions.assertEquals(
                2,
                new Solution().jump(new int[]{2,3,0,1,4})
        );

        Assertions.assertEquals(
                0,
                new Solution().jump(new int[]{0})
        );

        Assertions.assertEquals(
                1,
                new Solution().jump(new int[]{1,2})
        );

        Assertions.assertEquals(
                4,
                new Solution().jump(new int[]{1,1,1,1,1})
        );
    }

    public int jump(int[] nums) {
        if (nums.length <= 1) return 0;
        if (nums[0] >= nums.length - 1) return 1;

        int res = 1;
        int i = 0;
        while (i < nums.length) {
            int max = 0;
            int maxIndex = 0;
            for (int j = i + 1; j < i + 1 + nums[i]; j++) {
                if (nums[j] + j > max) {
                    max = nums[j] + j;
                    maxIndex = j;
                }
                if (max >= nums.length - 1) return ++res;
            }
            i = maxIndex;
            ++res;
        }
        return res;
    }

    public int jump2(int[] nums) {
        if (nums.length <= 1) return 0;

        int res = 0;
        int i = 0;
        int max = nums[0];
        int maxIndex = 0;

        while (i < nums.length) {
            if (max >= nums.length - 1) return ++res;

            int left = i + 1;
            int right = i + 1 + nums[i];
            for (int j = left; j < right; j++) {
                if (nums[j] + j > max) {
                    max = nums[j] + j;
                    maxIndex = j;
                }
            }

            i = maxIndex;
            ++res;
        }
        return -1;
    }

    public int jump3(int[] nums) {
        if (nums.length == 1) return 0;

        int curDistance = 0;
        int nextDistance = 0;
        int ans = 0;

        for (int i = 0; i < nums.length; i++) {
            nextDistance = Math.max(nums[i] + i, nextDistance);
            if (i == curDistance) {
                if (curDistance != nums.length - 1) {
                    ans++;
                    curDistance = nextDistance;
                    if (nextDistance >= nums.length - 1) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return ans;
    }

    public int jump4(int[] nums) {
        int curDistance = 0;
        int nextDistance = 0;
        int ans = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            // 移动到 nums.length - 2 的地方进行判断
            nextDistance = Math.max(nums[i] + i, nextDistance);
            if (i == curDistance) {
                ans++;
                curDistance = nextDistance;
            }
        }
        return ans;
    }

}