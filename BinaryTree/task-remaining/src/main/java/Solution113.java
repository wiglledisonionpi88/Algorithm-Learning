

import java.util.*;

/**
 * 113. 路径总和 II
 */
public class Solution113 {
    private final List<List<Integer>> res = new ArrayList<>();
    private final Deque<Integer> curPath = new ArrayDeque<>();
    public List<List<Integer>> pathSum1(TreeNode root, int targetSum) {
        if (root == null) return new ArrayList<>();
        pathSumHelper(root, targetSum);
        return res;
    }

    private void pathSumHelper(TreeNode root, int targetSum) {
        curPath.push(root.val);

        // 叶子节点
        if (root.left == null && root.right == null && root.val == targetSum) {
            LinkedList<Integer> list = new LinkedList<>();
            for (Integer i : curPath) {
                list.addFirst(i);
            }
            res.add(new ArrayList<>(list));
            return;
        }

        if (root.left != null) {
            pathSumHelper(root.left, targetSum - root.val);
            curPath.pop();
        }
        if (root.right != null) {
            pathSumHelper(root.right, targetSum - root.val);
            curPath.pop();
        }
    }

    /**
     * 迭代
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) return new ArrayList<>();

        ArrayList<List<Integer>> res = new ArrayList<>();
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        ArrayDeque<Pair<List<Integer>, Integer>> pathDeque = new ArrayDeque<>();

        deque.push(root);
        pathDeque.push(new Pair<>(new ArrayList<>(), 0));
        while (!deque.isEmpty()) {
            TreeNode node = deque.pop();
            Pair<List<Integer>, Integer> pair = pathDeque.pop();
            List<Integer> path = pair.first;
            Integer sum = pair.second;

            path.add(node.val);
            sum += node.val;

            if (node.left == null && node.right == null && sum == targetSum) {
                res.add(path);
            }

                if (node.right != null) {
                deque.push(node.right);
                pathDeque.push(new Pair<>(new ArrayList<>(path), sum));
            }
            if (node.left != null) {
                deque.push(node.left);
                pathDeque.push(new Pair<>(new ArrayList<>(path), sum));
            }
        }

        return res;
    }

    public static void main(String[] args) {
        //        TreeNode root = new TreeNode(1, new TreeNode(2), null);
//        [5,4,8,11,null,13,4,7,2,null,null,null,1]
        TreeNode root = new TreeNode(5, new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null), new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1))));
        System.out.println(new Solution113().pathSum(root, 22));
    }
}

class Pair<T, U> {
    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    public final T first;
    public final U second;
}