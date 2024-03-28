package task1to40.task27;

import base.TreeNode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 98.验证⼆叉搜索树
 */
public class Solution98 {
    /**
     * 中序遍历 判断是否递增
     */
    public boolean isValidBST1(TreeNode root) {
        ArrayList<Integer> nums = new ArrayList<>();
        inorder(root, nums);

        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i) >= nums.get(i + 1)) return false;
        }
        return true;
    }

    private void inorder(TreeNode root, ArrayList<Integer> nums) {
        if (root == null) return;

        inorder(root.left, nums);
        nums.add(root.val);
        inorder(root.right, nums);
    }

    /**
     * 递归中判断
     */
    long max = Long.MIN_VALUE;
    public boolean isValidBST2(TreeNode root) {
        if (root == null) return true;

        boolean left = isValidBST(root.left);

        if (root.val > max) {
            max = root.val;
        } else {
            return false;
        }

        boolean right = isValidBST(root.right);
        return left && right;
    }


    /**
     * 迭代
     */
    public boolean isValidBST3(TreeNode root) {
        if (root == null) return true;

        long max = Long.MIN_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode node = stack.pop();
                cur = node.right;
                if (node.val > max) {
                    max = node.val;
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 迭代
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        long max = Long.MIN_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                if (node.right != null) stack.push(node.right);
                stack.push(node);
                stack.push(null);
                if (node.left != null) stack.push(node.left);
            } else {
                node = stack.pop();
                if (node.val > max) {
                    max = node.val;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
