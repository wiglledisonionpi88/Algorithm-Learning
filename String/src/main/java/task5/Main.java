package task5;

import java.util.Scanner;

/**
 * 55. 右旋字符串（第八期模拟笔试）
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        scanner.nextLine();
        String s = scanner.nextLine();

        System.out.println(reverseString(s, count));
    }

    private static String reverseString(String s, int count) {
        char[] chars = s.toCharArray();
        reverse(chars, 0, chars.length - count - 1);
        reverse(chars, chars.length - count, chars.length - 1);
        reverse(chars, 0, chars.length - 1);
        return new String(chars);
    }

    private static void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char tmp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = tmp;
        }
    }
}
