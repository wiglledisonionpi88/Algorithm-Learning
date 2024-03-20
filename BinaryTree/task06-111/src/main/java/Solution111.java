import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 111. 二叉树的最小深度
 */
public class Solution111 {
//    [3,9,20,null,null,15,7]
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                new TreeNode(9, null, null), new TreeNode(20,
                new TreeNode(15, null, null), new TreeNode(7, null, null)));

        int minDepth = new Solution111().minDepth(root);
        System.out.println("minDepth = " + minDepth);
    }

    public int minDepth1(TreeNode root) {
        if (root == null) return 0;

        if (root.left != null && root.right == null) {
            return 1 + minDepth(root.left);
        }
        if (root.left == null && root.right != null) {
            return 1 + minDepth(root.right);
        }

        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    /**
     * 层序遍历 迭代法
     */

    public int minDepth(TreeNode root) {
        int depth = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }

        loop:
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
                if (node.left == null && node.right == null) {
                    break loop;
                }
            }
        }
        return depth;
    }
}
