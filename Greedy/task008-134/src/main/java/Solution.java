import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                3,
                new Solution().canCompleteCircuit(new int[]{1,2,3,4,5}, new int[] {3,4,5,1,2})
        );

        Assertions.assertEquals(
                -1,
                new Solution().canCompleteCircuit(new int[]{2,3,4}, new int[] {3,4,3})
        );
    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int length = gas.length;
        for (int i = 0; i < length; i++) {
            int rest = gas[i] - cost[i];
            int index = (i + 1) % length;
            while (rest >= 0) {
                rest = rest + gas[index] - cost[index];
                index = (index + 1) % length;
                if (index == i) {
                    return rest >= 0 ? i : -1;
                }
            }
        }
        return -1;
    }

    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < gas.length; i++) {
            int rest = gas[i] - cost[i];
            sum += rest;
            min = Math.min(min, sum);
        }

        // 无法跑完一圈
        if (sum < 0) return -1;

        // 在 0 开始就可以跑完一圈
        if (min >= 0) return 0;

        // 倒序寻找填平
        for (int i = gas.length - 1; i >= 0; i--) {
            int rest = gas[i] - cost[i];
            min += rest;
            if (min >= 0) {
                return i;
            }
        }

        return -1;
    }

    public int canCompleteCircuit3(int[] gas, int[] cost) {
        int curSum = 0;
        int totalSum = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            curSum += gas[i] - cost[i];
            totalSum += gas[i] - cost[i];

            if (curSum < 0) {
                curSum = 0;
                start = i + 1;
            }
        }
        return totalSum < 0 ? -1 : start;
    }
}