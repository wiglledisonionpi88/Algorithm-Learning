package task3;

import java.util.Scanner;

/**
 * 54. 替换数字（第八期模拟笔试）
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(format(s));
        int[] nums = new int[]{ 1,2,3 };
        String[] strings = new String[]{ "1","2","3" };
    }

    private static String format(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                stringBuilder.append("number");
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private static String format1(String s) {
        return s.replaceAll("\\d", "number");
    }
}
