package tree;

import tree.TreeNode;

import java.util.Objects;

/**
 * 计算一颗二叉树共有多少个节点
 *
 * @author : luoyong
 * @date : 2021-03-23 11:16
 **/
public class TreeNodeCount {

    public static void main(String[] args) {
        count(TreeNode.of());
    }

    public static int count(TreeNode root) {

        if (Objects.isNull(root)) {
            return 0;
        }

        return 1 + count(root.left) + count(root.right);
    }

}
