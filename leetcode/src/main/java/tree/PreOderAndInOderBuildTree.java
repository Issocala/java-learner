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
        return buildTree(preOder, 0, preOder.length - 1, inOder, 0, inOder.length - 1);
    }

    private static TreeNode buildTree(int[] preOder, int preStar, int preEnd,
                                  int[] inOder, int inStar, int inEnd) {
        if (preStar >= preEnd) {
            return null;
        }
        int rootVal = preOder[preStar];
        int index = 0;
        for (int i = inStar; i <= inEnd; i++) {
            if (inOder[i] == rootVal) {
                index = i;
                break;
            }
        }
        int leftSize = index - inStar;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(preOder, preStar + 1, preStar + leftSize,
                inOder, inStar, index - 1);
        root.right = buildTree(preOder, preStar + leftSize + 1, preEnd,
                inOder, index + 1, inEnd);
        return root;
    }
}
