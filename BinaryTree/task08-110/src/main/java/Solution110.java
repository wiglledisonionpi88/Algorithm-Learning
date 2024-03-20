import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 110. 平衡二叉树
 */
public class Solution110 {
    //    如何判断当前传入节点为根节点的⼆叉树是否是平衡⼆叉树呢，当然是左⼦树⾼度和右⼦树高度相差。
//    分别求出左右⼦树的⾼度，然后如果差值小于等于1，则返回当前⼆叉树的高度，否则则返回-1，表示已经不是二叉树了。
    public boolean isBalanced1(TreeNode root) {
        return isBalancedHelper(root) != -1;
    }

    private int isBalancedHelper(TreeNode root) {
        if (root == null) return 0;
        int lDepth = isBalancedHelper(root.left);
        if (lDepth == -1) return -1;
        int rDepth = isBalancedHelper(root.right);
        if (rDepth == -1) return -1;

        if (Math.abs(lDepth - rDepth) > 1) {
            // 返回-1，表示已经确定该二叉树不是平衡二叉树了
            return -1;
        } else {
            // 返回当前二叉树的高度
            return 1 + Math.max(lDepth, rDepth);
        }
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int lDepth = getDepth(node.left);
                int rDepth = getDepth(node.right);

                if (Math.abs(lDepth - rDepth) > 1) {
                    return false;
                }

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        return true;
    }

    private int getDepth(TreeNode treeNode) {
        if (treeNode == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(treeNode);

        int maxDepth = 0;
        int depth = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                depth++;
                stack.push(node);
                stack.push(null);

                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            } else {
                depth--;
                node = stack.pop();
            }
            if (depth > maxDepth) maxDepth = depth;
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(4, null, null), new TreeNode(4, null, null)), new TreeNode(3, null, null)), new TreeNode(2, null, null));

//        new Solution110().listTreeNode(root, 0);
        boolean balanced = new Solution110().isBalanced(root);
        System.out.println("balanced = " + balanced);
    }

    private void listTreeNode(TreeNode root, int depth) {
        if (root == null) return;

        System.out.println(" ".repeat(depth * 4) + root.val);

        listTreeNode(root.left, depth + 1);
        listTreeNode(root.right, depth + 1);
    }
}
