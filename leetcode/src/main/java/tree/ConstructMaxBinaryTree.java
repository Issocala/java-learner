package tree;

/**
 * 力扣第 654 题
 * 构建最大二叉树
 *
 * @author : luoyong
 * @date : 2021-03-24 10:25
 **/
public class ConstructMaxBinaryTree {

    public static void main(String[] args) {
        constructMaxTree(new int[]{1, 2, 6, 3, 4, -1});
    }

    private static TreeNode constructMaxTree(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private static TreeNode build(int[] nums, int l, int h) {
        if (l >= h) {
            return null;
        }
        int index = -1;
        int max = Integer.MIN_VALUE;
        for (int i = l; i <= h; i++) {
            if (nums[i] > max) {
                max = nums[i];
                index = i;
            }
        }

        TreeNode root = new TreeNode(max);

        root.left = build(nums, l, index - 1);
        root.right = build(nums, index + 1, h);

        return root;
    }

}
