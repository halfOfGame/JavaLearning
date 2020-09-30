package Offer;

/**
 * @author halfOfGame
 * @create 2020-04-01,8:26
 */
public class Question_68_2 {
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
        if (root == null || root == p || root == q)
            return root;

        TreeNode leftAncestor = lowestCommonAncestor(root.left, p, q);
        TreeNode rightAncestor = lowestCommonAncestor(root.right, p, q);

        if (leftAncestor == null)
            return rightAncestor;
        if (rightAncestor == null)
            return leftAncestor;

        return root;
    }
}
