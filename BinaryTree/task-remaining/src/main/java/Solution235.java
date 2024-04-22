/**
 * 235. 二叉搜索树的最近公共祖先
 */
public class Solution235 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        int max = Math.max(p.val, q.val);
        int min = Math.min(p.val, q.val);
        if (root.val >= min && root.val <= max) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    /**
     * 利用搜索二叉树的特点，查找第一个在区间[p, q]的值
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }

        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(new Integer[]{6, 2, 8, 0, 4, 7, 9, null, null, 3, 5});
        TreeNode node2 = root.left;
        TreeNode node4 = root.left.right;

        TreeNode res = new Solution235().lowestCommonAncestor(root, node2, node4);
        System.out.println(res);
    }
}
