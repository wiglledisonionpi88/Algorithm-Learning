import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 559. N 叉树的最大深度
 */
public class Solution559 {

    /**
     * 递归
     */
    public int maxDepth1(Node root) {
        if (root == null) return 0;

        int max = 0;
        for (Node child : root.children) {
            int depth = maxDepth(child);
            if (max < depth) max = depth;
        }

        return max + 1;
    }

    /**
     * 迭代 层序遍历
     */
    public int maxDepth(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }
        int depth = 0;
        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                for (Node child : node.children) {
                    if (child != null) {
                        queue.offer(child);
                    }
                }
            }
        }
        return depth;
    }
}
