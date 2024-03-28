package task1to40.task11;

import java.util.Scanner;

/**
 * 卡码
 * 55. 右旋字符串（第八期模拟笔试）
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        scanner.nextLine();
        String str = scanner.nextLine();

        String res = rotate(str, num);
        System.out.println(res);
    }

    private static String rotate(String str, int num) {
        char[] chars = str.toCharArray();
        reverse(chars, 0, chars.length - 1 - num);
        reverse(chars, chars.length - num, chars.length - 1);
        reverse(chars, 0, chars.length - 1);
        return new String(chars);
    }

    private static void reverse(char[] chars, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }
    }
}
