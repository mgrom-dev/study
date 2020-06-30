package binary_tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeTest {
    BinaryTree tree;

    @BeforeEach
    void setUp() {
        tree = new BinaryTree();
        tree.addNode("root");
    }

    @Test
    void addNode() {
        tree.addNode("left");
        tree.addNode("rt");
        Node root = tree.searchNodes("root").get(0);
        assertEquals("left", root.getLeft().getData());
        assertEquals("rt", root.getRight().getData());
    }

    @Test
    void searchNodes() {
        Node root = tree.searchNodes("root").get(0);
        assertEquals("root", root.getData());
    }
}