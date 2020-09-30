package DataStructuresAndAlgorithmAnalysisThirdEdition;


import java.util.*;

public class MyTree {

    public TreeNode head;

    //定义树的数据结构
    public static class TreeNode {
        TreeNode left;
        TreeNode right;
        Integer data;


        public TreeNode(TreeNode left, TreeNode right, Integer data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }

    //---------------------------初始化二叉树----------------------------
    public MyTree(Integer[] nums) {
        initTree(nums);
    }

    //按照层填充生成一颗树,先用一个ArrayList存储生成的节点，然后根据完全二叉树的规律构建二叉树
    public void initTree(Integer[] nums) {
        int size = nums.length;
        List<TreeNode> nodes = new ArrayList();
        for (Integer num : nums) {
            if (num == null)
                nodes.add(null);
            else
                nodes.add(new TreeNode(null, null, num));
        }
        for (int i = 0; i < size / 2 + 1; i++) {
            if (nodes.get(i) != null) {
                if (2 * i + 1 < size)
                    nodes.get(i).left = nodes.get(2 * i + 1);
                if (2 * i + 2 < size)
                    nodes.get(i).right = nodes.get(2 * i + 2);
            }
        }
        head = nodes.get(0);
    }

    //-----------------------------先序遍历------------------------------
    //递归先序遍历树
    public void preTraverseTree_Recursion(TreeNode head) {
        if (head != null) {
            System.out.print(head.data + " ");
            preTraverseTree_Recursion(head.left);
            preTraverseTree_Recursion(head.right);
        }
    }

    /**
     * 迭代先序遍历树
     * 先将head进栈，当栈不为空时
     * {将栈顶元素tempNode弹出，tempNode的右孩子左孩子依次进栈(不为空时)}
     */
    public void preTraverseTree_Iteration(TreeNode head) {
        if (head == null) {
            return;
        }
        Deque<TreeNode> stack = new ArrayDeque();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode tempNode = stack.pop();
            System.out.print(tempNode.data + " ");
            if (tempNode.right != null)
                stack.push(tempNode.right);
            if (tempNode.left != null)
                stack.push(tempNode.left);
        }
    }

    //-----------------------------中序遍历---------------------------------
    //递归中序遍历树
    public void inTraverseTree_Recursion(TreeNode head) {
        if (head != null) {
            inTraverseTree_Recursion(head.left);
            System.out.print(head.data + " ");
            inTraverseTree_Recursion(head.right);
        }
    }


    /**
     * 迭代中序遍历树
     * cur表示是否右孩子需要进栈，当栈不为空或者cur不为null时
     * {左孩子一直进栈，直到cur为null，出栈tempNode，如果tempNode右孩子不为空，则进栈并令cur = tempNode.right}
     */

    public void inTraverseTree_Iteration(TreeNode head) {
        if (head == null)
            return;
        Deque<TreeNode> stack = new ArrayDeque();
        TreeNode cur = head;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode tempNode = stack.pop();
            System.out.print(tempNode.data + " ");
            if (tempNode.right != null)
                cur = tempNode.right;

        }
    }

    //-------------------------------后序遍历-------------------------------------
    //递归后序遍历树
    public void postTraverseTree_Recursion(TreeNode head) {
        if (head != null) {
            postTraverseTree_Recursion(head.left);
            postTraverseTree_Recursion(head.right);
            System.out.print(head.data + " ");
        }
    }

    /**
     * 迭代后序遍历树,cur表示上一次输出的节点，当栈不为空时
     * {
     * 当栈顶节点的左孩子不为空，并且左右孩子都没有输出过时，将左孩子入栈(因为右孩子都输出过了则左孩子一定输出过了)，
     * 否则，当栈顶节点的右孩子不为空，并且右孩子没有输出过时，将右孩子入栈
     * 否则，栈顶节点出栈，并改变lastNode
     * }
     */
    public void postTraverseTree_Iteration(TreeNode head) {
        if (head == null)
            return;
        //cur表示上一次出栈的节点
        TreeNode lastNode = head;
        Deque<TreeNode> stack = new ArrayDeque();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.peek();
            //当右孩子都输出了则左孩子一定已经输出过了
            if (peek.left != null && peek.left != lastNode && peek.right != lastNode) {
                stack.push(peek.left);
            } else if (peek.right != null && peek.right != lastNode) {
                stack.push(peek.right);
            } else {
                lastNode = stack.pop();
                System.out.print(lastNode.data + " ");
            }
        }
    }


    //-----------------------------层序遍历---------------------------------

    /**按层次遍历树
     *
     *
     */
    public void levelTraverseTree_Normal(TreeNode head) {
        Queue<TreeNode> queue = new LinkedList();
        queue.add(head);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print(temp.data + " ");
            if (temp.left != null)
                queue.add(temp.left);
            if (temp.right != null)
                queue.add(temp.right);
        }
    }

    /**按层次遍历二叉树，并且打印出层次结构
     *
     * 每次循环都输出一层
     */
    public void levelTraverseTree_Special(TreeNode head) {
        if (head == null)
            return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode tempNode = queue.poll();
                System.out.print(tempNode.data + " ");
                if (tempNode.left != null)
                    queue.add(tempNode.left);
                if (tempNode.right != null)
                    queue.add(tempNode.right);
            }
            System.out.println();
        }
    }


    /**
     * 返回p和q共同的祖先节点
     *
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;

        TreeNode leftNode = lowestCommonAncestor(root.left, p, q);
        TreeNode rightNode = lowestCommonAncestor(root.right, p, q);

        if (leftNode == null)
            return rightNode;
        if (rightNode == null)
            return leftNode;
        return root;
    }
}
