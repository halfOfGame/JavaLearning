package Offer;

/**
 * @author halfOfGame
 * @create 2020-04-01,8:06
 */

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 */
public class Question_68_1 {

    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        Integer val;


        public TreeNode(TreeNode left, TreeNode right, Integer data) {
            this.left = left;
            this.right = right;
            this.val = data;
        }
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val < p.val && root.val < q.val)
                root = root.right;
            else if (root.val > p.val && root.val > q.val)
                root = root.left;
            else
                return root;
        }
        return root;
    }
}
