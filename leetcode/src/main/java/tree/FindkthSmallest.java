package tree;

import java.util.Objects;

/**
 * 力扣第 230 题「二叉搜索树中第K小的元素」
 */

public class FindkthSmallest {

    //当前第几小
    private static int rank = 0;
    //结果
    private static int res = 0;

    public static void main(String[] args) {
        kthSmallest(new TreeNode(), 3);
    }

    private static int kthSmallest(TreeNode root, int k) {
        traverse(root, k);
        return 0;
    }



    private static void traverse(TreeNode root, int k) {
        if (Objects.isNull(root)) {
            return;
        }
        traverse(root.left, k);

        rank++;

        if (rank == k) {
            res = root.value;
            return;
        }

        traverse(root.right, k);

    }

}
