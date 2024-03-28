package task1to40.task31;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合
 */
public class Solution77 {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    /**
     * 递归 回溯
     */
    public List<List<Integer>> combine(int n, int k) {
        // res.clear();
        // path.clear();
        combineHelper(n, k, 1);
        return res;
    }

    /**
     * 原版
     * @param n n个数字（1 ~ n）
     * @param k 要选择几个数字
     * @param start 开始的数字
     */
    private void combineHelper1(int n, int k, int start) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= n; i++) {
            path.add(i);
            combineHelper(n, k, i + 1);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 剪枝优化
     * @param n n个数字（1 ~ n）
     * @param k 要选择几个数字
     * @param start 开始的数字
     */
    private void combineHelper(int n, int k, int start) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }

        // path.size()      当前已选择的个数
        // n - i + 1        当前还可以选择的最大个数
        // k                要选择几个数字
        // path.size() + n - i + 1 >= k
        // i <= path.size() + n + 1 - k
        for (int i = start; i <= path.size() + n + 1 - k; i++) {
            path.add(i);
            combineHelper(n, k, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> combine = new Solution77().combine(4, 2);
        System.out.println("combine = " + combine);
    }
}
