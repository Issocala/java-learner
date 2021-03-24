package tree;

/**
 * 力扣第 105 题  通过前序和中序遍历结果构造二叉树
 *
 * @author : luoyong
 * @date : 2021-03-24 11:32
 **/
public class PreOderAndInOderBuildTree {

    public static void main(String[] args) {
        int[] preOder = {3, 9, 20, 15, 7};
        int[] inOder = {9, 3, 15, 20, 7};
        buildTree(preOder, inOder);
    }

    private static TreeNode buildTree(int[] preOder, int[] inOder) {
        return buildTree(preOder, 0, preOder.length - 1,
                inOder, 0, inOder.length - 1);
    }

    private static TreeNode buildTree(int[] preOder, int preStart, int preEnd, int[] inOder, int inStart, int inEnd) {
        if (preStart >= preEnd) {
            return null;
        }
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOder[i] == preOder[preStart]) {
                index = i;
                break;
            }
        }

        int leftSize = index - inStart;

        TreeNode root = new TreeNode(preOder[preStart]);

        root.left = buildTree(preOder, preStart + 1, preStart + leftSize,
                inOder, inStart, index - 1);

        root.right = buildTree(preOder, preStart + leftSize + 1, preEnd,
                inOder, leftSize + 1, inEnd);
        return root;
    }


}
