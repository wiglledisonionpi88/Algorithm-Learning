import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * ⼆叉树的层序遍历
 */
public class Solution102 {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        ArrayList<List<Integer>> res = new ArrayList<>();

        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            ArrayList<Integer> layer = new ArrayList<>();
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                layer.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            res.add(layer);
        }

        return res;
    }
}
