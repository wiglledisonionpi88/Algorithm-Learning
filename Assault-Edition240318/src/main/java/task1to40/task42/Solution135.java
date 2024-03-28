package task1to40.task42;

/**
 * 135. 分发糖果
 */
public class Solution135 {
    public int candy(int[] ratings) {
        int[] candies = new int[ratings.length];

        // 保证向左符合题目要求
        candies[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                candies[i] = 1;
            }
        }

        // 保证向右符合题目要求
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
                candies[i] = candies[i + 1] + 1;
            }
        }

        int sum = 0;
        for (int num : candies) {
            sum += num;
        }
        return sum;
    }

    public static void main(String[] args) {
        int res = new Solution135().candy(new int[]{1, 3, 4, 5, 2});
        System.out.println(res);
    }
}
