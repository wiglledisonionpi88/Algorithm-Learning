package task08;

import java.util.ArrayList;

public class Solution349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        boolean[] set = new boolean[1000];

        for (int i : nums1) {
            set[i] = true;
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i : nums2) {
            if (set[i]) {
                list.add(i);
                set[i] = false;
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
