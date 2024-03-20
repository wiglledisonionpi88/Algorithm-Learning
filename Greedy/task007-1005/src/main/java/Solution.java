import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                5,
                new Solution().largestSumAfterKNegations(new int[]{4,2,3}, 1)
        );
    }
    public int largestSumAfterKNegations(int[] nums, int k) {
        List<Integer> list = Arrays.stream(nums)
                .boxed()
                .sorted()
                .collect(Collectors.toList());

        for (int i = list.size() - 1; i >= 0 && k > 0; i--) {
            if (list.get(i) < 0) {
                list.set(i, -list.get(i));
                k--;
            }
        }

        if (k % 2 == 1) {
            list.set(0, -list.get(0));
        }

        return list.stream()
                .reduce(Integer::sum)
                .get();
    }
}