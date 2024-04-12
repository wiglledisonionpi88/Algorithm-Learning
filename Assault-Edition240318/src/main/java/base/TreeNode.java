package base;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(Integer[] nums) {
        if (nums.length == 0) return;

        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        this.val = nums[0];
        nodes.offer(this);
        int index = 1;

        while (!nodes.isEmpty()) {
            int size = nodes.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = nodes.poll();
                if (index < nums.length && nums[index] != null) {
                    node.left = new TreeNode(nums[index]);
                    nodes.offer(node.left);
                }
                ++index;

                if (index < nums.length && nums[index] != null) {
                    node.right = new TreeNode(nums[index]);
                    nodes.offer(node.right);
                }
                ++index;
            }
        }
    }

    @Override
    public String toString() {
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        nodes.offer(this);
        ArrayList<Integer> res = new ArrayList<>();
        res.add(this.val);

        while (!nodes.isEmpty()) {
            int size = nodes.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = nodes.poll();

                if (node.left != null) {
                    nodes.offer(node.left);
                    res.add(node.left.val);
                } else {
                    res.add(null);
                }
                if (node.right != null) {
                    nodes.offer(node.right);
                    res.add(node.right.val);
                } else {
                    res.add(null);
                }
            }
        }
        return res.toString();
    }
}
