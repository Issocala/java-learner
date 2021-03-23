package tree;

import java.util.Objects;

/**
 * 力扣第 116 题  连接每一层节点
 *
 * @author : luoyong
 * @date : 2021-03-23 15:11
 **/
public class ConnectTree {

    static class Node {
        Node left;
        Node right;
        Node next;
    }

    public static void main(String[] args) {
        connect(new Node());
    }

    private static Node connect(Node root) {
        if (Objects.isNull(root)) {
            return null;
        }

        //连接左右子树
        connectTwo(root.left, root.right);

        return root;
    }

    private static void connectTwo(Node node1, Node node2) {
        if (Objects.isNull(node1) || Objects.isNull(node2)) {
            return;
        }
        node1.next = node2;
        connectTwo(node1.left, node2.right);
        connectTwo(node2.left, node2.right);
        connectTwo(node1.right, node2.left);
    }
}
