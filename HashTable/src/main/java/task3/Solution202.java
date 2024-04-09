package task3;

import java.util.HashSet;
import java.util.Set;

/**
 * 202. 快乐数
 */
public class Solution202 {
    public boolean isHappy1(int n) {
        HashSet<Integer> set = new HashSet<>();
        set.add(n);

        do {
            int sum = 0;
            while (n > 0) {
                int digit = n % 10;
                sum += digit * digit;
                n /= 10;
            }
            if (sum == 1) {
                return true;
            }
            n = sum;
        } while (set.add(n));
        return false;
    }

    public boolean isHappy(int n) {
        Set<Integer> record = new HashSet<>();
        while (n != 1 && !record.contains(n)) {
            record.add(n);
            n = getNextNumber(n);
        }
        return n == 1;
    }

    private int getNextNumber(int n) {
        int res = 0;
        while (n > 0) {
            int temp = n % 10;
            res += temp * temp;
            n = n / 10;
        }
        return res;
    }

    public static void main(String[] args) {
        boolean res = new Solution202().isHappy(19);
        System.out.println(res);
    }
}
