package task01;

import java.util.Arrays;

/**
 * 704. 二分查找
 */
public class Solution704 {
    public static void main(String[] args) {
        int n = 3;
        int[][] matrix = new Solution704().generateMatrix(n);
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
    public int[][] generateMatrix1(int n) {
        int[][] res = new int[n][n];
        int top = 0, right = n - 1, bottom = n - 1, left = 0;
        int flag = 0;
        int count = 1;
        while (count <= n * n) {
            if (flag % 4 == 0) {
//                left --> right
                for (int i = left; i <= right; i++) {
                    res[top][i] = count++;
                }
                top++;
            } else if (flag % 4 == 1) {
//                top --> bottom
                for (int i = top; i <= bottom; i++) {
                    res[i][right] = count++;
                }
                right--;
            } else if (flag % 4 == 2) {
//                bottom --> left
                for (int i = right; i >= left; i--) {
                    res[bottom][i] = count++;
                }
                bottom--;
            } else {
//                bottom --> top
                for (int i = bottom; i >= top; i--) {
                    res[i][left] = count++;
                }
                left++;
            }
            flag++;
        }
        return res;
    }

    public int[][] generateMatrix(int n) {
        int count = 1;
        int loop = -1;
        int[][] res = new int[n][n];

        while (loop++ < n / 2) {
            // left --> right
            for (int i = loop; i <= n - loop - 1; i++) {
                res[loop][i] = count++;
            }

            // top --> bottom
            for (int i = loop + 1; i <= n - loop - 1; i++) {
                res[i][n - loop - 1] = count++;
            }

            // right --> left
            for (int i = n - loop - 2; i >= loop; i--) {
                res[n - loop - 1][i] = count++;
            }

            // bottom --> top
            for (int i = n - loop - 2; i >= loop + 1; i--) {
                res[i][loop] = count++;
            }
        }
        if (n % 2 == 1) {
            res[n / 2][n / 2] = n * n;
        }
        return res;
    }
}
