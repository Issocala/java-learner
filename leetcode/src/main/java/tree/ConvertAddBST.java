package tree;

import java.util.Objects;

/**
 * 第 538 题    BST 转化累加树
 */
public class ConvertAddBST {

    private static int sum = 0;

    public static void main(String[] args) {
        convertBST(TreeNode.of());
    }

    private static TreeNode convertBST(TreeNode root) {

        traverse(root);

        return root;
    }

    private static void traverse(TreeNode root) {
        if (Objects.isNull(root)) {
            return;
        }

        traverse(root.right);

        root.value += sum;

        traverse(root.left);

    }

}
