import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * ⼆叉树的中序遍历
 */
public class Solution94 {
    public static void main(String[] args) {
//        TreeNode root = new TreeNode(1,
//                null, new TreeNode(2,
//                new TreeNode(3, null, null), null));

//        [3,1,null,null,2]
        TreeNode root = new TreeNode(3,
                new TreeNode(1, null, new TreeNode(2, null, null)), null);

        List<Integer> integers = new Solution94().inorderTraversal(root);
        System.out.println(integers);
    }

    // 递归
    public List<Integer> inorderTraversal1(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        preorderTraversalHelper(res, root);
        return res;
    }

    private void preorderTraversalHelper(ArrayList<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }

        preorderTraversalHelper(list, root.left);
        list.add(root.val);
        preorderTraversalHelper(list, root.right);
    }

    /**
     * 从左下开始
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();

        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                // 压栈到左下角
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode node = stack.pop();    // 左 中
                res.add(node.val);
                if (node.right != null) {
                    cur = node.right;   // 右
                }
            }
        }
        return res;
    }


    public List<Integer> inorderTraversal(TreeNode root) {
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

                stack.push(node);           // 中
                stack.push(new Object());

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

    /**
     * 使用 Stack 可以存 null
     */
    public List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                if (node.right != null) {
                    stack.push(node.right);
                }

                stack.push(node);
                stack.push(null);

                if (node.left != null) {
                    stack.push(node.left);
                }
            } else {
                node = stack.pop();
                res.add(node.val);
            }
        }

        return res;
    }
}
