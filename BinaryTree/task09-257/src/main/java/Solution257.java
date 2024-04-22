import java.util.*;

/**
 * 257. 二叉树的所有路径
 */
public class Solution257 {
//    public List<String> binaryTreePaths(TreeNode root) {
//        if (root == null) return new ArrayList<>();
//
//        ArrayList<String> res = new ArrayList<>();
//        binaryTreePathsHelper(root, res, new StringBuilder(root.val));
//        return res;
//    }
//
//    private void binaryTreePathsHelper(TreeNode root, ArrayList<String> res, StringBuilder curPath) {
//        if (root.left == null && root.right == null) {
//            res.add(curPath.toString());
//            return;
//        }
//        curPath = new StringBuilder(curPath);
//        curPath.append("->")
//                .append(root.val);
//
//        binaryTreePathsHelper(root.left, res, curPath);
//        binaryTreePathsHelper(root.right, res, curPath);
//    }

    public List<String> binaryTreePaths1(TreeNode root) {
        ArrayList<String> res = new ArrayList<>();
        if (root == null) return res;
        String path = "";
        traversal(root, res, path);
        return res;
    }

    private void traversal(TreeNode root, ArrayList<String> res, String path) {
        path += root.val;
        if (root.left == null && root.right == null) {
            res.add(path);
            return;
        }
        if (root.left != null) {
            traversal(root.left, res, path + "->");
        }
        if (root.right != null) {
            traversal(root.right, res, path + "->");
        }
    }

    public List<String> binaryTreePaths2(TreeNode root) {
        if (root == null) return new ArrayList<>();

        ArrayList<String> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        Stack<String> paths = new Stack<>();

        stack.push(root);
        paths.push(String.valueOf(root.val));
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            String path = paths.pop();

            if (node.left == null && node.right == null) {
                res.add(path);
            }

            if (node.right != null) {
                stack.push(node.right);
                paths.push(path + "->" + node.right.val);
            }
            if (node.left != null) {
                stack.push(node.left);
                paths.push(path + "->" + node.left.val);
            }
        }

        return res;
    }


    List<String> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {
        helper(root);
        return res;
    }

    private void helper(TreeNode root) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            // the end
            StringBuilder s = new StringBuilder();
            for (Integer integer : path) {
                s.append(integer).append("->");
            }
            s.append(root.val);
            res.add(s.toString());
            return;
        }

        path.add(root.val);

        helper(root.left);
        helper(root.right);

        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2, null, new TreeNode(5, null, null)), new TreeNode(3, null, null));
        List<String> list = new Solution257().binaryTreePaths(root);
        list.forEach(System.out::println);
    }
}






















