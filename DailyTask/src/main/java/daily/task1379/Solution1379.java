package daily.task1379;

import daily.base.TreeNode;

/**
 * 1379. 找出克隆二叉树中的相同节点
 */
public class Solution1379 {
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null || original == target) {
            return cloned;
        }

        TreeNode leftSubtree = getTargetCopy(original.left, cloned.left, target);
        if (leftSubtree != null) return leftSubtree;

        return getTargetCopy(original.right, cloned.right, target);
    }
}
