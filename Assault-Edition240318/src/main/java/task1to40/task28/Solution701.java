package task1to40.task28;
import base.TreeNode;
/**
 * 701. 二叉搜索树中的插入操作
 */
public class Solution701 {
    /**
     * 迭代
     */
    public TreeNode insertIntoBST1(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        TreeNode cur = root;
        while (true) {
            if (val > cur.val) {
                if (cur.right == null) {
                    cur.right = new TreeNode(val);
                    break;
                }
                else cur = cur.right;
            } else {
                if (cur.left == null) {
                    cur.left = new TreeNode(val);
                    break;
                }
                else cur = cur.left;
            }
        }
        return root;
    }

    /**
     * 递归
     */
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        insertIntoBSTHelper(root, val);
        return root;
    }

    private void insertIntoBSTHelper(TreeNode root, int val) {
        // '>' or '<'
        if (root.val > val) {
            if (root.left == null) root.left = new TreeNode(val);
            else insertIntoBSTHelper(root.left, val);
        } else {
            if (root.right == null) root.right = new TreeNode(val);
            else insertIntoBSTHelper(root.right, val);
        }
    }


    /**
     * 递归
     * 返回子树
     */
    public TreeNode insertIntoBST3(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

    /**
     * 迭代
     * 缓存 pre 节点
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null) {
            pre = cur;
            if (cur.val > val) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        if (pre.val > val) {
        // if (pre.left == null) {
            pre.left = new TreeNode(val);
        } else {
            pre.right = new TreeNode(val);
        }

        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(new Integer[]{4, 2, 7, 1, 3});
        TreeNode node = new Solution701().insertIntoBST(root, 5);
        System.out.println("node = " + node);
    }
}
