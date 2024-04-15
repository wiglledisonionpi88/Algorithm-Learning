package daily.task448;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 448. 找到所有数组中消失的数字
 */
public class Solution448 {
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i < nums.length + 1; i++) {
            if (!set.contains(i)) {
                res.add(i);
            }
        }
        return res;
    }

    public List<Integer> findDisappearedNumbers2(int[] nums) {
        boolean[] set = new boolean[nums.length + 1];
        for (int num : nums) {
            set[num] = true;
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i < nums.length + 1; i++) {
            if (!set[i]) {
                res.add(i);
            }
        }
        return res;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;

        for (int num : nums) {
            nums[(num - 1) % n] += n;
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (nums[i - 1] <= n) {
                res.add(i);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{ 4,3,2,7,8,2,3,1 };
        List<Integer> disappearedNumbers = new Solution448().findDisappearedNumbers(nums);
        System.out.println(disappearedNumbers);
    }
}
