import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertEquals(
                5,
                new Solution().candy(new int[] {1,0,2})
        );

        Assertions.assertEquals(
                4,
                new Solution().candy(new int[] {1,2,2})
        );

        Assertions.assertEquals(
                11,
                new Solution().candy(new int[] {1,3,4,5,2})
        );
    }
    public int candy(int[] ratings) {
        int[] candies = new int[ratings.length];
        candies[0] = 1;

        // 从前向后
        for (int i = 1; i < candies.length; i++) {
            candies[i] = ratings[i] > ratings[i - 1] ? candies[i - 1] + 1 : 1;
        }

        // 从后向前
        for (int i = candies.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        int res = 0;
        for (int candy : candies) {
            res += candy;
        }
        return res;
    }
}