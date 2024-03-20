import java.util.Arrays;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 */
public class Solution106 {
    public static void main(String[] args) {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postorder = {9,15,7,20,3};
        TreeNode treeNode = new Solution106().buildTree(inorder, postorder);
    }
//    public TreeNode buildTree(int[] inorder, int[] postorder) {
//        if (inorder.length == 0) return null;
//
//        TreeNode root = new TreeNode();
//        buildTreeHelper(root, inorder, postorder);
//        return root;
//    }
//
//    private void buildTreeHelper(TreeNode node, int[] inorder, int[] postorder) {
//        if (inorder.length == 0) return;
//
//        node.val = postorder[postorder.length - 1];
//        int inorderIndex = indexOf(inorder, node.val);
//
//
//        int[] inorderLeft = Arrays.copyOfRange(inorder, 0, inorderIndex);
//        int[] inorderRight = Arrays.copyOfRange(inorder, inorderIndex + 1, inorder.length);
//        int[] postOrderLeft = Arrays.copyOfRange(postorder, 0, inorderIndex);
//        int[] postOrderRight = Arrays.copyOfRange(postorder, inorderIndex, postorder.length - 1);
//
//        if (inorderLeft.length != 0) {
//            node.left = new TreeNode();
//            buildTreeHelper(node.left, inorderLeft, postOrderLeft);
//        }
//
//        if (inorderRight.length != 0) {
//            node.right = new TreeNode();
//            buildTreeHelper(node.right, inorderRight, postOrderRight);
//        }
//    }
//
//    private int indexOf(int[] arr, int val) {
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] == val) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0) return null;

        TreeNode root = new TreeNode();
        buildTreeHelper(root, inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
        return root;
    }

    private void buildTreeHelper(TreeNode node, int[] inorder, int iStart, int iEnd, int[] postorder, int pStart, int pEnd) {
        if (inorder.length == 0) return;

        node.val = postorder[pEnd];
        int inorderIndex = indexOf(inorder, iStart, iEnd, node.val);
        if (inorderIndex == -1) return;

        int leftCnt = inorderIndex - iStart;
        int rightCnt = iEnd - inorderIndex;

        if (leftCnt > 0) {
            node.left = new TreeNode();
            buildTreeHelper(
                    node.left,
                    inorder, iStart,  iStart + leftCnt - 1,
                    postorder, pStart, pStart + leftCnt - 1);
        }

        if (rightCnt > 0) {
            node.right = new TreeNode();
            buildTreeHelper(
                    node.right,
                    inorder, iEnd - rightCnt + 1, iEnd,
                    postorder, pEnd - rightCnt, pEnd - 1);
        }
    }

    private int indexOf(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return i;
            }
        }
        return -1;
    }

    private int indexOf(int[] arr, int begin, int end, int val) {
        for (int i = begin; i <= end; i++) {
            if (arr[i] == val) {
                return i;
            }
        }
        return -1;
    }
}
