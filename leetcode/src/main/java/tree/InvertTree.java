package tree;

import java.util.Objects;

/**
 * 力扣第 226 题「翻转二叉树」，输入一个二叉树根节点root，让你把整棵树镜像翻转
 * <p>
 * 4
 * /   \
 * 2     7
 * / \   / \
 * 1   3 6   9
 * <p>
 * 4
 * /   \
 * 7     2
 * / \   / \
 * 9   6 3   1
 *
 * @author : luoyong
 * @date : 2021-03-23 11:27
 **/
public class InvertTree {

    public static void main(String[] args) {
        invertTree(new TreeNode());
    }

    private static TreeNode invertTree(TreeNode root) {

        if (Objects.isNull(root)) {
            return null;
        }

        change(root);

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    public static void change(TreeNode node) {
        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
    }
}
