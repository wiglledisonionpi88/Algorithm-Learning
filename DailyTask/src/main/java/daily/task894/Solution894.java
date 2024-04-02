package daily.task894;

import daily.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 894. 所有可能的真二叉树
 */
public class Solution894 {
    // List<TreeNode> res = new ArrayList<>();
    // TreeNode root;
    //
    // public List<TreeNode> allPossibleFBT(int n) {
    //     if (n % 2 == 0) return new ArrayList<>();
    //     root = new TreeNode(0);
    //     allPossibleFBTHelper(n - 1, root);
    //     return res;
    // }
    //
    // private void allPossibleFBTHelper(int n, TreeNode node) {
    //     if (n == 0) {
    //         res.add(copyTree(root));
    //         return;
    //     }
    //
    //     // 添加
    //     node.left = new TreeNode(0);
    //     node.right = new TreeNode(0);
    //
    //     if (n == 2) {
    //         allPossibleFBTHelper(0, node.left);
    //     } else {
    //         allPossibleFBTHelper(n - 2, node.left);
    //         allPossibleFBTHelper(n - 2, node.right);
    //     }
    //
    //     if (n >= 6) {
    //         node.left.left = new TreeNode(0);
    //         node.left.right = new TreeNode(0);
    //         node.right.left = new TreeNode(0);
    //         node.right.right = new TreeNode(0);
    //         if (n == 6) {
    //             allPossibleFBTHelper(0, node.left.left);
    //         } else {
    //             allPossibleFBTHelper(n - 6, node.left.right);
    //             allPossibleFBTHelper(n - 6, node.right.left);
    //             allPossibleFBTHelper(n - 6, node.right.right);
    //         }
    //     }
    //
    //     // 回溯
    //     node.left = null;
    //     node.right = null;
    // }
    //
    // private TreeNode copyTree(TreeNode root) {
    //     if (root == null) return null;
    //     TreeNode node = new TreeNode(root.val);
    //     node.left = copyTree(root.left);
    //     node.right = copyTree(root.right);
    //     return node;
    // }

    public List<TreeNode> allPossibleFBT(int n) {
        List<TreeNode> res = new ArrayList<>();
        if (n % 2 == 0) return res;

        if (n == 1) {
            res.add(new TreeNode(0));
            return res;
        }

        // 枚举左右子树的节点情况
        for (int i = 1; i <= n - 1; i += 2) {
            List<TreeNode> leftSubtrees = allPossibleFBT(i);
            List<TreeNode> rightSubtrees = allPossibleFBT(n - 1 - i);

            for (TreeNode leftSubtree : leftSubtrees) {
                for (TreeNode rightSubtree : rightSubtrees) {
                    res.add(new TreeNode(0, leftSubtree, rightSubtree));
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<TreeNode> res = new Solution894().allPossibleFBT(7);
        res.forEach(System.out::println);
    }
}
