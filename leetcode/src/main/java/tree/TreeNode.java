package tree;

/**
 * @author : luoyong
 * @date : 2021-03-23 11:16
 **/
public class TreeNode {

    int value;

    TreeNode left;

    TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static TreeNode of() {
        return new TreeNode();
    }

    public static TreeNode of(int value) {
        return new TreeNode(value);
    }

    public static TreeNode of(int value, TreeNode left, TreeNode right) {
        return new TreeNode(value, left, right);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
