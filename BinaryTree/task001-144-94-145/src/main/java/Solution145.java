import java.util.*;

/**
 * ⼆叉树的后序遍历
 */
public class Solution145 {

    // 递归
    public List<Integer> postorderTraversal1(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        preorderTraversalHelper(res, root);
        return res;
    }

    private void preorderTraversalHelper(ArrayList<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }

        preorderTraversalHelper(list, root.left);
        preorderTraversalHelper(list, root.right);
        list.add(root.val);
    }

    // 迭代
    public List<Integer> postorderTraversal2(TreeNode root) {
        // LinkedList终于有了它的作用
        LinkedList<Integer> res = new LinkedList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();

        // 先序遍历  中 右 左
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            res.addFirst(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        ArrayDeque<Object> stack = new ArrayDeque<>();

        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            if (stack.peek() instanceof TreeNode) {
                TreeNode node = (TreeNode) stack.pop();

                stack.push(node);           // 中
                stack.push(new Object());

                if (node.right != null) {   // 右
                    stack.push(node.right);
                }

                if (node.left != null) {    // 左
                    stack.push(node.left);
                }
            } else {
                stack.pop();
                res.add(((TreeNode) stack.pop()).val);
            }
        }

        return res;
    }
}
