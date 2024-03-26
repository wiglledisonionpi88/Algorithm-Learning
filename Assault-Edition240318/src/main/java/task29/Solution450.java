package task29;

import base.TreeNode;

/**
 * 450. 删除二叉搜索树中的节点
 */
public class Solution450 {
    /**
     * 递归
     */
    public TreeNode deleteNode1(TreeNode root, int key) {
        // 没找到删除的节点，遍历到空节点直接返回了
        if (root == null) return null;

        if (root.val > key) {
            // left
            root.left = deleteNode(root.left, key);
            return root;
        } else if (root.val < key) {
            // right
            root.right = deleteNode(root.right, key);
            return root;
        } else {
            // equal
            if (root.left == null && root.right == null) {
                // 左右孩子都为空（叶子节点），直接删除节点， 返回NULL为根节点
                return null;
            } else if (root.left == null) {
                // 删除节点的左孩子为空，右孩子不为空，删除节点，右孩子补位，返回右孩子为根节点
                return root.right;
            } else if (root.right == null) {
                // 删除节点的右孩子为空，左孩子不为空，删除节点，左孩子补位，返回左孩子为根节点
                return root.left;
            } else {
                // 左右孩子节点都不为空，则将删除节点的左子树头结点（左孩子）放到删除节点的右子树的最左面节点的左孩子上，
                // 返回删除节点右孩子为新的根节点。
                // 左子树 放到 右子树 的左下角
                TreeNode rightSubTreeLeftBottomNode = root.right;
                while (rightSubTreeLeftBottomNode.left != null) {
                    rightSubTreeLeftBottomNode = rightSubTreeLeftBottomNode.left;
                }
                rightSubTreeLeftBottomNode.left = root.left;

                // 删除这个节点
                return root.right;
            }
        }
    }

    /**
     * 迭代
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode dummyRoot = new TreeNode(0, root, null);
        TreeNode pre = dummyRoot;

        while (root != null) {
            if (root.val == key) break;
            pre = root;
            if (root.val > key) {
                root = root.left;
            } else {
                root = root.right;
            }
        }

        if (root == null) return dummyRoot.left;

        if (pre.left == root) {
            pre.left = deleteNode(root);
        } else {
            pre.right = deleteNode(root);
        }

        return dummyRoot.left;
    }

    public TreeNode deleteNode(TreeNode node) {
        if (node == null) return null;
        if (node.right == null) return node.left;

        TreeNode rightSubTreeLeftBottomNode = node.right;
        while (rightSubTreeLeftBottomNode.left != null) {
            rightSubTreeLeftBottomNode = rightSubTreeLeftBottomNode.left;
        }
        rightSubTreeLeftBottomNode.left = node.left;
        return  node.right;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(new Integer[]{3,2,5,null,null,4,10,null,null,8,15,7});
        TreeNode node = new Solution450().deleteNode(root, 5);
        System.out.println("node = " + node);
    }
}
