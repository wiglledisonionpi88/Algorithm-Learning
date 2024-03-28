package task1to40.task43;

/**
 * 860. 柠檬水找零
 */
public class Solution860 {
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;

        for (int bill : bills) {
            switch (bill) {
                case 5:
                    five++;
                    break;

                case 10:
                    if (five == 0) return false;
                    five--;
                    ten++;
                    break;

                case 20:
                    if (five >= 1 && ten >= 1) {
                        // 10 + 5
                        five--;
                        ten--;
                    } else if (five >= 3) {
                        // 5 + 5 + 5
                        five -= 3;
                    } else {
                        return false;
                    }
                    break;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        // 输入：bills = [5,5,5,10,20]
        // 输出：true
        boolean res = new Solution860().lemonadeChange(new int[]{5, 5, 5, 10, 20});
        System.out.println(res);
    }
}
