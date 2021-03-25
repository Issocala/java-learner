package tree;

import java.util.Objects;

/**
 * 校验是否是二叉搜索树（BST）
 */
public class IsValidBST {
    public static void main(String[] args) {
        isValidBST(TreeNode.of());
    }

    private static boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    private static boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (Objects.isNull(root)) {
            return true;
        }
        if (Objects.nonNull(min) && root.value <= min.value) {
            return false;
        }

        if (Objects.nonNull(max) && root.value >= max.value) {
            return false;
        }

        return isValidBST(root.left, min, root)
                && isValidBST(root.right, root, max);
    }
}
