package task1to40.task26;

import base.TreeNode;

/**
 * 700.⼆叉搜索树登场
 */
public class Solution700 {
    /**
     * 迭代
     */
    public TreeNode searchBST1(TreeNode root, int val) {
        while (root != null && root.val != val) {
            if (root.val > val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return root;
    }

    /**
     * 递归
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val > val) {
            return searchBST(root.left, val);
        } else if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return root;
        }
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) return root;

        if (root.val > val) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }
}
