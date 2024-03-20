import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

/**
 * 404. 左叶子之和
 */
public class Solution100 {
    public int sumOfLeftLeaves1(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0;

        // 左叶子节点
        if (root.left != null && (root.left.left == null && root.left.right == null)) {
            return root.left.val + sumOfLeftLeaves1(root.left) + sumOfLeftLeaves1(root.right);
        }
        return sumOfLeftLeaves1(root.left) + sumOfLeftLeaves1(root.right);
    }

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0;
        int sum = 0;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            boolean traversalLeft = true;
            TreeNode node = stack.pop();
            if (node.left != null && node.left.left == null && node.left.right == null) {
                traversalLeft = false;
                sum += node.left.val;
            }
            if (traversalLeft && node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return sum;
    }
}
