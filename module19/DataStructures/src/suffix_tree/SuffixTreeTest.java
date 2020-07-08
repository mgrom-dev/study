package suffix_tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuffixTreeTest {
    SuffixTree suffixTree;

    @BeforeEach
    void setUp() {
        suffixTree = new SuffixTree("this text tightly some terrible sophisticated text");
    }

    @Test
    void search() {
        suffixTree.search("some");
        assertEquals(18, suffixTree.search("some").get(0));
    }
}