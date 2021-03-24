package tree;

import java.util.Objects;

/**
 * 力扣第 114 题 将二叉树展开为链表
 * 把一棵树拉平成一条链表
 *
 * @author : luoyong
 * @date : 2021-03-24 09:35
 **/
public class FlattenTree {

    public static void main(String[] args) {
        flatten(new TreeNode());
    }

    // 定义：将以 root 为根的树拉平为链表
    private static void flatten(TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = null;
        root.right = left;

        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }

        p.right = right;
    }
}
