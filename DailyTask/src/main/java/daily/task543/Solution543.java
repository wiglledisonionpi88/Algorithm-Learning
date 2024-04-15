package daily.task543;

import daily.base.TreeNode;

/**
 * 543. 二叉树的直径
 */
public class Solution543 {
    int max = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        helper(root);
        return max;
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;

        int lDepth = helper(root.left);
        int rDepth = helper(root.right);

        // 库函数太慢，不用
        max = lDepth + rDepth > max ? lDepth + rDepth : max;

        return (lDepth > rDepth ? lDepth : rDepth) + 1;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(new Integer[]{1,2,3,4,5});
        int i = new Solution543().diameterOfBinaryTree(treeNode);
        System.out.println(i);
    }
}
