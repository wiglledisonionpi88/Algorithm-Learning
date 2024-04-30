package com.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. 复原 IP 地址
 */
public class Solution93 {
    List<String> res = new ArrayList<>();
    StringBuilder stringBuilder;

    public List<String> restoreIpAddresses(String s) {
        if (s.length() > 12) return res;
        stringBuilder = new StringBuilder(s);
        helper(1, 0);
        return res;
    }

    private void helper(int startIndex, int count) {
        if (count == 3) {
            if (validate(stringBuilder.toString())) {
                res.add(stringBuilder.toString());
            }
            return;
        }

        for (int i = startIndex; i < stringBuilder.length() && i < startIndex + 3; i++) {
            if (isValid(stringBuilder.lastIndexOf("."), i))
            stringBuilder.insert(i, ".");
            helper(i + 2, count + 1);
            stringBuilder.deleteCharAt(i);
        }
    }

    private boolean isValid(int start, int end) {
        String str = stringBuilder.substring(start, end);
        if (str.isEmpty()) return false;
        if (str.charAt(0) == '0' && str.length() > 1) return false;
        if (Integer.parseInt(str) > 255) return false;

        return true;
    }

    private boolean validate(String str) {
        for (String s : str.split("\\.")) {
            if (s.isEmpty()) return false;
            int n = Integer.parseInt(s);
            // 值必须小于 255
            if (n > 255) return false;

            // 值不能前导 0
            if (s.charAt(0) == '0' && s.length() > 1) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String str = "25525511135";
        List<String> res = new Solution93().restoreIpAddresses(str);
        System.out.println(res);
    }
}
