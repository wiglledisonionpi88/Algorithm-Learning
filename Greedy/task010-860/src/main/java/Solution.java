import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

class Solution {
    public static void main(String[] args) {
        Assertions.assertTrue(
                new Solution().lemonadeChange(new int[]{5,5,5,10,20})
        );

        Assertions.assertFalse(
                new Solution().lemonadeChange(new int[]{5,5,10,10,20})
        );
    }
    public boolean lemonadeChange(int[] bills) {
        int[] money = new int[3];
        Arrays.fill(money, 0);

        for (int bill : bills) {
            switch (bill) {
                case 5:
                    money[0]++;
                    break;

                case 10:
                    money[1]++;
                    if (money[0] >= 1) {
                        // 5
                        money[0]--;
                    } else {
                        return false;
                    }
                    break;

                case 20:
                    money[2]++;
                    if (money[1] >= 1 && money[0] >= 1) {
                        // 10 + 5
                        money[1]--;
                        money[0]--;
                    } else if (money[0] >= 3) {
                        // 5 * 3
                        money[0] -= 3;
                    } else {
                        return false;
                    }
            }
        }
        return true;
    }

    public boolean lemonadeChange2(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                ten++;
                if (five == 0) return false;
                five--;
            } else {
                if (ten >= 1 && five >= 1) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}