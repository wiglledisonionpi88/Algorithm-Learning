import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 637. 二叉树的层平均值
 */
public class Solution637 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(1, null, null), null);
        List<Double> averages = new Solution637().averageOfLevels(root);
        System.out.println(averages);
    }
    public List<Double> averageOfLevels(TreeNode root) {
        ArrayList<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();

        if (root != null) {
            queue.offer(root);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                sum += node.val;
            }
            res.add(sum / size);
        }

        return res;
    }
}
