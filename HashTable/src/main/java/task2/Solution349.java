package task2;

import java.util.ArrayList;

public class Solution349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        boolean[] map = new boolean[1001];
        ArrayList<Integer> list = new ArrayList<>();

        for (int i : nums1) {
            map[i] = true;
        }

        for (int i : nums2) {
            if (map[i]) {
                list.add(i);
                map[i] = false;
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
