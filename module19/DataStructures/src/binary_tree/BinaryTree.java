package binary_tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree
{
    private Node root;

    public void addNode(String data)
    {
        Node node = new Node(data);
        if (root == null) {
            root = node;
        } else {
            Node current = root;
            while(node.getParent() == null) {
                if (current.getData().compareTo(data) > 0) {
                    if (current.getLeft() == null) {
                        node.setParent(current);
                        current.setLeft(node);
                    }
                    current = current.getLeft();
                } else {
                    if (current.getRight() == null) {
                        node.setParent(current);
                        current.setRight(node);
                    }
                    current = current.getRight();
                }
            }
        }
    }

    public List<Node> searchNodes(String data)
    {
        List<Node> nodes = new ArrayList<>();
        Node node = root;
        while(node != null) {
            int side = node.getData().compareTo(data);
            if (side > 0) {
                node = node.getLeft();
            } else {
                if (side == 0) {
                    nodes.add(node);
                }
                node = node.getRight();
            }
        }
        return nodes;
    }
}