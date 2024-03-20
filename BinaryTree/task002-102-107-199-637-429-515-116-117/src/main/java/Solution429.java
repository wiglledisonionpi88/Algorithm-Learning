import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 429. N 叉树的层序遍历
 */
public class Solution429 {
    public List<List<Integer>> levelOrder1(Node root) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();

        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            ArrayList<Integer> layer = new ArrayList<>();
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                layer.add(node.val);
                node.children.forEach(queue::offer);
            }

            res.add(layer);
        }
        return res;
    }



    /**
     * 深搜 dfs
     */
    ArrayList<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> levelOrder(Node root) {
        levelOrderHelper(root, 0);
        return res;
    }
    public void levelOrderHelper(Node root, int level) {
        if (root == null) return;

        if (res.size() - 1 < level) {
            res.add(new ArrayList<>());
        }
        res.get(level).add(root.val);
        for (Node child : root.children) {
            levelOrderHelper(child, level + 1);
        }
    }
}
