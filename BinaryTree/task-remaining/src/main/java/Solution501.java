import java.util.*;

/**
 * 501. 二叉搜索树中的众数
 */
public class Solution501 {
    // PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((arr1, arr2) -> arr2[1] - arr1[1]);
    // int cur = 0;
    // int count = 0;
    //
    // Map<Integer, Integer> map = new HashMap<>();
    // public int[] findMode(TreeNode root) {
    //     helper(root);
    //     priorityQueue.offer(new int[]{cur, count});
    //
    //     int max = priorityQueue.peek()[1];
    //     ArrayList<Integer> list = new ArrayList<>();
    //     while (!priorityQueue.isEmpty() && priorityQueue.peek()[1] == max) {
    //         list.add(priorityQueue.poll()[0]);
    //     }
    //
    //     int[] arr = new int[list.size()];
    //     for (int i = 0; i < list.size(); i++) {
    //         arr[i] = list.get(i);
    //     }
    //
    //     return arr;
    // }
    //
    // private void helper(TreeNode root) {
    //     if (root == null) return;
    //     helper(root.left);
    //
    //     if (cur == root.val) {
    //         count++;
    //     } else {
    //         priorityQueue.offer(new int[]{cur, count});
    //         cur = root.val;
    //         count = 1;
    //     }
    //
    //     helper(root.right);
    // }

    ArrayList<int[]> list = new ArrayList<>();
    int count = 0;
    TreeNode pre;
    public int[] findMode(TreeNode root) {
        helper(root);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i)[0];
        }
        return res;
    }

    private void helper(TreeNode root) {
        if (root == null) return;

        helper(root.left);

        if (pre == null || root.val != pre.val) {
            pre = root;
            count = 1;
        } else {
            count++;
        }

        if (list.isEmpty() || count > list.get(0)[1]) {
            list.clear();
            list.add(new int[]{pre.val, count});
        } else if (count == list.get(0)[1]) {
            list.add(new int[]{pre.val, count});
        }

        helper(root.right);
    }
}



















