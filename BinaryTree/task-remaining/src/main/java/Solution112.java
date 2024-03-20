import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 112. 路径总和
 */
public class Solution112 {
    public static void main(String[] args) {
//        TreeNode root = new TreeNode(1, new TreeNode(2), null);
//        [5,4,8,11,null,13,4,7,2,null,null,null,1]
        TreeNode root = new TreeNode(5, new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null), new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1))));

        System.out.println(new Solution112().hasPathSum(root, 22));
    }
    /**
     * 递归
     */
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        if (root == null) return false;
        return hasPathSumHelper(root, targetSum, 0);
    }

    private boolean hasPathSumHelper(TreeNode root, int targetSum, int curSum) {
        // null，直接返回false，因为是 || 运算
        if (root == null) {
            return false;
        }
        // 叶子节点
        if (root.left == null && root.right == null) {
            return curSum + root.val == targetSum;
        }
        return hasPathSumHelper(root.left, targetSum, curSum + root.val)
                || hasPathSumHelper(root.right, targetSum, curSum + root.val);
    }


    /**
     * 迭代
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        Deque<TreeNode> nodeDeque = new ArrayDeque<>();
        Deque<Integer> curSumDeque = new ArrayDeque<>();

        nodeDeque.push(root);
        curSumDeque.push(root.val);
        while (!nodeDeque.isEmpty()) {
            TreeNode node = nodeDeque.pop();
            Integer curSum = curSumDeque.pop();

            // 叶子节点
            if (node.left == null && node.right == null && curSum == targetSum) {
                return true;
            }

            if (node.right != null) {
                nodeDeque.push(node.right);
                curSumDeque.push(curSum + node.right.val);
            }
            if (node.left != null) {
                nodeDeque.push(node.left);
                curSumDeque.push(curSum + node.left.val);
            }
        }
        return false;
    }
}
