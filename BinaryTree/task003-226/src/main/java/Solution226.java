import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 226. 翻转二叉树
 */
public class Solution226 {

    /**
     * 递归
     */
    public TreeNode invertTree1(TreeNode root) {
        invertTreeHelper(root);
        return root;
    }

    private void invertTreeHelper(TreeNode root) {
        if (root == null) {
            return;
        }

        // 翻转
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;

        invertTreeHelper(root.left);
        invertTreeHelper(root.right);
    }

    /**
     * 前序遍历
     */
    public TreeNode invertTree2(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }

            // 翻转
            TreeNode temp = node.right;
            node.right = node.left;
            node.left = temp;
        }
        return root;
    }

    /**
     * 前序遍历
     */
    public TreeNode invertTree3(TreeNode root) {
        Deque<Object> stack = new ArrayDeque<>();

        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            Object obj = stack.pop();
            if (obj instanceof TreeNode) {
                TreeNode node = ((TreeNode) obj);

                if (node.right != null) {       // 右
                    stack.push(node.right);
                }

                if (node.left != null) {        // 左
                    stack.push(node.left);
                }

                stack.push(node);               // 中
                stack.push(new Object());
            } else {
                TreeNode n = (TreeNode) stack.pop();
                // 翻转
                TreeNode temp = n.right;
                n.right = n.left;
                n.left = temp;
            }
        }

        return root;
    }


    /**
     * 中序遍历
     */
    public TreeNode invertTree4(TreeNode root) {
        Deque<Object> stack = new ArrayDeque<>();

        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            Object obj = stack.pop();
            if (obj instanceof TreeNode) {
                TreeNode node = ((TreeNode) obj);

                if (node.right != null) {       // 右
                    stack.push(node.right);
                }

                stack.push(node);               // 中
                stack.push(new Object());

                if (node.left != null) {        // 左
                    stack.push(node.left);
                }
            } else {
                TreeNode n = (TreeNode) stack.pop();
                // 翻转
                TreeNode temp = n.right;
                n.right = n.left;
                n.left = temp;
            }
        }

        return root;
    }

    /**
     * 层序遍历
     */
    public TreeNode invertTree(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                // 翻转
                TreeNode temp = node.right;
                node.right = node.left;
                node.left = temp;
            }
        }
        return root;
    }
}
