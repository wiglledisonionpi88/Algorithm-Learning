package task28;
import base.TreeNode;
/**
 * 701. 二叉搜索树中的插入操作
 */
public class Solution701 {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        TreeNode cur = root;

        while (cur != null) {
            if (cur.left == null && cur.right == null) {
                TreeNode newNode = new TreeNode(val);
                if (val > cur.val) {
                    cur.right = newNode;
                } else {
                    cur.left = newNode;
                }
                break;
            }

            if (val > cur.val) {
                cur = cur.right;
            } else if (val < cur.val) {
                cur = cur.left;
            }
        }
        return root;
    }
}
