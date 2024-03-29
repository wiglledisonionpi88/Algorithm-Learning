package task51;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <a href="https://www.programmercarl.com/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%98%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80%E5%AE%8C%E5%85%A8%E8%83%8C%E5%8C%85.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE">52. 携带研究材料（第七期模拟笔试）<a/>
 */
public class Main {
/*
3 4
3 15
2 20
1 30
*/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int types = scanner.nextInt();
        int bagSize = scanner.nextInt();

        int[] weight = new int[types];
        int[] value = new int[types];
        for (int i = 0; i < types; i++) {
            weight[i] = scanner.nextInt();
            value[i] = scanner.nextInt();
        }

        System.out.println(getMaxSize(bagSize, weight, value));
    }

    /**
     * 一维数组
     */
    private static int getMaxSize(int bagSize, int[] weight, int[] value) {
        int[] dp = new int[bagSize + 1];

        for (int i = 0; i < weight.length; i++) {
            for (int j = weight[i]; j < bagSize + 1; j++) {
                // dp[j - weight[i]]：为这次的物品腾出空间后的最大价值（可重复）
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[bagSize];
    }

    // /**
    //  * 二维数组
    //  */
    // private static int getMaxSize(int bagSize, int[] weight, int[] value) {
    //     int[][] dp = new int[weight.length][bagSize + 1];
    //
    //     // 初始化
    //     for (int i = weight[0]; i < bagSize + 1; i++) {
    //         dp[0][i] = value[0] * (i / weight[0]);
    //     }
    //
    //     // dp
    //     for (int i = 1; i < weight.length; i++) {
    //         for (int j = 1; j < bagSize + 1; j++) {
    //             if (weight[i] <= j) {
    //                 dp[i][j] = dp[i - 1][j];
    //                 for (int k = 1; k <= j / weight[i]; k++) {
    //                     dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * weight[i]] + k * value[i]);
    //                 }
    //             } else {
    //                 dp[i][j] = dp[i - 1][j];
    //             }
    //         }
    //     }
    //
    //     for (int[] ints : dp) {
    //         System.out.println(Arrays.toString(ints));
    //     }
    //     return dp[weight.length - 1][bagSize];
    // }
}
