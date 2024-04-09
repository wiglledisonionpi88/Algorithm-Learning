package task7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 15. 三数之和
 */
public class Solution15 {
    public List<List<Integer>> threeSum(int[] nums) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        Map<Integer, int[]> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length; j++) {
                map.put(nums[i] + nums[j], new int[]{i, j});
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int[] indexes = map.get(-nums[i]);
            if (indexes != null && indexes[0] != i && indexes[1] != i) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(nums[i]);
                list.add(nums[indexes[0]]);
                list.add(nums[indexes[1]]);
                res.add(list);
            }
        }
        return res;
    }
}
