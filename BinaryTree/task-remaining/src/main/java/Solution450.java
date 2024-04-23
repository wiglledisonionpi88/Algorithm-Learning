/**
 * 左孩子替代当前节点的位置
 */
public class Solution450 {
    public TreeNode deleteNode1(TreeNode root, int key) {
        if (root == null) return null;

        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                insertNode(root.left, root.right);
                root.right = null;
                return root.left;
            }
        }

        return root;
    }

    public TreeNode insertNode(TreeNode root, TreeNode node) {
        if (root == null) return node;

        if (root.val > node.val) {
            root.left = insertNode(root.left, node);
        } else if (root.val < node.val) {
            root.right = insertNode(root.right, node);
        }

        return root;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode cur = root.left;
                while (cur.right != null) {
                    cur = cur.right;
                }
                cur.right = root.right;
                return root.left;
            }
        }

        return root;
    }
}
