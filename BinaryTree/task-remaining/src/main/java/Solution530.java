/**
 * 530. 二叉搜索树的最小绝对差
 */
public class Solution530 {
    int pre = -1;
    int res = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        helper(root);
        return res;
    }

    private void helper(TreeNode root) {
        if (root == null) return;

        helper(root.left);

        if (pre == -1) {
            pre = root.val;
        } else {
            res = Math.min(Math.abs(pre - root.val), res);
            if (res == 0) return; // 最小绝对差为0
            pre = root.val;
        }

        helper(root.right);
    }
}
