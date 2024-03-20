import java.util.*;

/**
 * ⼆叉树的层序遍历
 */
public class Solution107 {
    public List<List<Integer>> levelOrderBottom1(TreeNode root) {
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

        Collections.reverse(res);
        return res;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        LinkedList<List<Integer>> res = new LinkedList<>();

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

            res.addFirst(layer);
        }

        return res;
    }
}
