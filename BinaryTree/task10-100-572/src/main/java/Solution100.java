import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 100. 相同的树
 */
public class Solution100 {
    public boolean isSameTree1(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSameTree1(p.left, q.left) && isSameTree1(p.right, q.right);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(q);
        queue.offer(p);
        while (!queue.isEmpty()) {
            p = queue.poll();
            q = queue.poll();

            if (p.val != q.val) {
                return false;
            }

            if (p.left != null && q.left != null) {
                queue.offer(q.left);
                queue.offer(p.left);
            } else if (p.left != null || q.left != null) {
                return false;
            }

            if (p.right != null && q.right != null) {
                queue.offer(q.right);
                queue.offer(p.right);
            } else if (p.right != null || q.right != null) {
                return false;
            }
        }
        return true;
    }
}
