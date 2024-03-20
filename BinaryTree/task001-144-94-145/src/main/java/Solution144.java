import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * ⼆叉树的前序遍历
 */
public class Solution144 {

    // 递归
    public List<Integer> preorderTraversal1(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        preorderTraversalHelper(res, root);
        return res;
    }

    private void preorderTraversalHelper(ArrayList<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }

        list.add(root.val);
        preorderTraversalHelper(list, root.left);
        preorderTraversalHelper(list, root.right);
    }

    // 迭代

    /**
     *   Stack
     * 3 左
     * 2 右
     * 1 中 直接加入到res
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();

        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return res;
    }

    /**
     * 统一的迭代写法
     * 标记null之后处理法
     * 标记处理法
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        ArrayDeque<Object> stack = new ArrayDeque<>();

        if (root != null) {
            stack.push(root);
        }

        while (!stack.isEmpty()) {
            if (stack.peek() instanceof TreeNode) {
                TreeNode node = (TreeNode) stack.pop();

                if (node.right != null) {   // 右
                    stack.push(node.right);
                }

                if (node.left != null) {    // 左
                    stack.push(node.left);
                }

                stack.push(node);           // 中
                stack.push(new Object());
            } else {
                stack.pop();
                res.add(((TreeNode) stack.pop()).val);
            }
        }

        return res;
    }
}
