import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 513. 找树左下角的值
 */
public class Solution513 {
    /**
     * 层序遍历
     */
    public int findBottomLeftValue1(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode leftNode = queue.peek();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            if (queue.isEmpty()) return leftNode.val;
        }
        return -1;
    }

    /**
     * 递归 前序遍历
     */
    int leftNodeVal = 0;
    int level = 0;  // 记录第一层的值
    public int findBottomLeftValue(TreeNode root) {
        findBottomLeftValueHelper(root, 1);
        return leftNodeVal;
    }

    private void findBottomLeftValueHelper(TreeNode root, int curLevel) {
        if (root == null) return;

        if (root.left == null && root.right == null && curLevel > level) {
            leftNodeVal = root.val;
            level = curLevel;
            return;
        }

        findBottomLeftValueHelper(root.left, curLevel + 1);
        findBottomLeftValueHelper(root.right, curLevel + 1);
    }
}
