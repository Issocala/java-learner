package tree;

import java.util.Objects;

/**
 *  LeetCode 第 236 题， 二叉树的最近公共祖先
 */
public class LowestCommonAncestor {

    public static void main(String[] args) {
        lowestCommonAncestor(TreeNode.of(), TreeNode.of(), TreeNode.of());
    }

    private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (Objects.isNull(root)) {
            return null;
        }

        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (Objects.nonNull(left) && Objects.nonNull(right)) {
            return root;
        }

       if (Objects.isNull(left) && Objects.isNull(right)) {
           return null;
       }
        return Objects.isNull(left) ? right : left;
    }

}
