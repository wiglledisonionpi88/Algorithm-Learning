package task48to49;

import java.util.Scanner;

/**
 * <a href="https://www.programmercarl.com/%E8%83%8C%E5%8C%85%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%8001%E8%83%8C%E5%8C%85-1.html#%E6%80%9D%E8%B7%AF">
 *     动态规划：01背包理论基础
 * </a>
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int typeNum = scanner.nextInt();
        int bagSize = scanner.nextInt();

        int[] weight = new int[typeNum];
        int[] value = new int[typeNum];
        for (int i = 0; i < typeNum; i++) {
            weight[i] = scanner.nextInt();
        }

        for (int i = 0; i < typeNum; i++) {
            value[i] = scanner.nextInt();
        }

        System.out.println(getMaxValue(bagSize, weight, value));
    }

    /**
     * 二维数组
     */
    private static int getMaxValue1(int bagSize, int[] weight, int[] value) {
        int[][] dp = new int[weight.length][bagSize + 1];

        // 初始化第一行
        for (int i = weight[0]; i < bagSize + 1; i++) {
            dp[0][i] = value[0];
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 1; j < bagSize + 1; j++) {
                if (j < weight[i])
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
            }
        }
        // System.out.println(Arrays.deepToString(dp));
        return dp[weight.length - 1][bagSize];
    }

    /**
     * 一维数组
     */
    private static int getMaxValue2(int bagSize, int[] weight, int[] value) {
        int[] dp = new int[bagSize + 1];

        for (int i = weight[0]; i < bagSize + 1; i++) {
            dp[i] = value[0];
        }
        for (int i = 1; i < weight.length; i++) {
            for (int j = bagSize; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[bagSize];
    }

    private static int getMaxValue(int bagSize, int[] weight, int[] value) {
        int[] dp = new int[bagSize + 1];

        for (int i = 0; i < weight.length; i++) {
            // j < weight[i] 放不下weight[i]了，直接继承
            for (int j = bagSize; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[bagSize];
    }
}
