import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 117.填充每个节点的下⼀个右侧节点指针II
 */
public class Solution117 {
    public Node connect(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            Node preNode = null;

            for (int i = 0; i < size; i++) {
                Node node;
                if (i == 0) {
                    preNode = queue.poll();
                    node = preNode;
                } else {
                    node = queue.poll();
                    preNode.next = node;
                    preNode = node;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return root;
    }
}
