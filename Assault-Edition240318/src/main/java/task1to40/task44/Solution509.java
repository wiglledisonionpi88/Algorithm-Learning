package task1to40.task44;

/**
 * 509. 斐波那契数
 */
public class Solution509 {
    public int fib1(int n) {
        if (n <= 1) return n;
        int[] nums = new int[n + 1];
        nums[0] = 0;
        nums[1] = 1;

        for (int i = 2; i < nums.length; i++) {
            nums[i] = nums[i - 1] + nums[i - 2];
        }
        return nums[n];
    }

    public int fib2(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }

    public int fib(int n) {
        return n < 2 ? n : fib(n - 1) + fib(n - 2);
    }
}
