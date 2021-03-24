package tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * 力扣第 652 题「寻找重复子树」
 *
 * @author : luoyong
 * @date : 2021-03-24 15:41
 **/
public class FindDuplicateSubtrees {

    public static Map<String, Integer> tree2CountMap = new HashMap<>();

    public static LinkedList<TreeNode> treeNodeList = new LinkedList<>();

    public static final String NULL_TREE = "#";

    public static void main(String[] args) {
        findDuplicateSubtrees(new TreeNode());
    }

    private static LinkedList<TreeNode> findDuplicateSubtrees(TreeNode root) {
        findBuild(root);
        return treeNodeList;
    }

    private static String findBuild(TreeNode root) {
        if (Objects.isNull(root)) {
            return NULL_TREE;
        }

        String left = findBuild(root.left);
        String right = findBuild(root.right);

        String treeString = left + right + root.value;

        int num = tree2CountMap.getOrDefault(treeString, 0);

        if (num == 1) {
            treeNodeList.add(root);
        }

        tree2CountMap.put(treeString, num + 1);

        return treeString;
    }

}
