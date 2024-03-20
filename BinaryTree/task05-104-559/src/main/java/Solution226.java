import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 104. 二叉树的最大深度
 */
public class Solution226 {

    /**
     * 递归
     */
    int max = 0;
    public int maxDepth1(TreeNode root) {
        maxDepthHelper(root, 1);
        return max;
    }

    private void maxDepthHelper(TreeNode root, int depth) {
        if (root == null) return;

        if (max < depth) max = depth;

        maxDepthHelper(root.left, depth + 1);
        maxDepthHelper(root.right, depth + 1);
    }


    /**
     * 递归
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 层序遍历 迭代
     */
    public int maxDepth(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        int depth = 0;

        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return depth;
    }


}
