import java.util.*;

/**
 * ⼆叉树的层序遍历
 */
public class Solution102 {
    public List<List<Integer>> levelOrder1(TreeNode root) {
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

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelOrderHelper(root, res, 1);
        return res;
    }

    private void levelOrderHelper(TreeNode root, List<List<Integer>> res, int layer) {
        if (root == null) return;

        while (res.size() < layer) {
            res.add(new ArrayList<>());
        }

        res.get(layer - 1).add(root.val);

        levelOrderHelper(root.left, res, layer + 1);
        levelOrderHelper(root.right, res, layer + 1);
    }

}
