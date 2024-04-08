package task5;

import java.util.Arrays;

/**
 * 59. 螺旋矩阵 II
 */
public class Solution59 {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];

        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = n - 1;
        int direction = 0;
        int x = 0;
        int y = 0;

        for (int i = 1; i <= n * n; i++) {
            switch (direction % 4) {
                case 0:
                    res[y][x] = i;
                    if (x == right) {
                        y++;
                        top++;
                        direction++;
                    } else x++;
                    break;
                case 1:
                    res[y][x] = i;
                    if (y == bottom) {
                        x--;
                        right--;
                        direction++;
                    } else y++;
                    break;
                case 2:
                    res[y][x] = i;
                    if (x == left) {
                        y--;
                        bottom--;
                        direction++;
                    } else x--;
                    break;
                case 3:
                    res[y][x] = i;
                    if (y == top) {
                        x++;
                        left++;
                        direction++;
                    } else y--;
                    break;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] res = new Solution59().generateMatrix(10);
        for (int[] l : res) {
            System.out.println(Arrays.toString(l));
        }
    }
}
