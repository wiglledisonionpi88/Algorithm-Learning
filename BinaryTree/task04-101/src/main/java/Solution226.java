import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 101. 对称二叉树
 */
public class Solution226 {

    /**
     * 递归
     */
    public boolean isSymmetric1(TreeNode root) {
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode lNode, TreeNode rNode) {
        if (lNode == null && rNode == null) {
            return true;
        } else if (lNode == null || rNode == null) {
            return false;
        } else {
            return lNode.val == rNode.val
                    && isSymmetricHelper(lNode.left, rNode.right)
                    && isSymmetricHelper(lNode.right, rNode.left);
        }
    }

    /**
     * 后序遍历  左右中 右左中
     * 前序遍历  中左右 中右左  Y 栈
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) {
            return true;
        } else if (root.left == null || root.right == null) {
            return false;
        }

        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        stack1.push(root.left);
        stack2.push(root.right);

        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            TreeNode node1 = stack1.pop();
            TreeNode node2 = stack2.pop();

            if (node1.val != node2.val) return false;

            if (node1.left != null && node2.right != null) {
                stack1.push(node1.left);
                stack2.push(node2.right);
            } else if (node1.left != null || node2.right != null) {
                return false;
            }

            if (node1.right != null && node2.left != null) {
                stack1.push(node1.right);
                stack2.push(node2.left);
            } else if (node1.right != null || node2.left != null) {
                return false;
            }
        }
        return true;
    }


    public boolean isSymmetric(TreeNode root) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            if (node1.val != node2.val) {
                return false;
            }

            if (node1.left != null && node2.right != null) {
                queue.offer(node1.left);
                queue.offer(node2.right);
            } else if (node1.left != null || node2.right != null) {
                return false;
            }

            if (node1.right != null && node2.left != null) {
                queue.offer(node1.right);
                queue.offer(node2.left);
            } else if (node1.right != null || node2.left != null) {
                return false;
            }
        }
        return true;
    }
}
