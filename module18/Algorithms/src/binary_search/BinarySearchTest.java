package binary_search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {
    private final ArrayList<String> WORDS = new ArrayList<>();
    private BinarySearch binarySearch;

    @BeforeEach
    void setUp() {
        WORDS.addAll(Arrays.asList("Программирование — это сложно. Основные правила, на которых все строится, очень просты, но по мере разработки программа сама начинает вводить свои правила и законы. Таким образом, программист строит лабиринт, в котором сам же может и потеряться.".split(" ")));
        WORDS.sort(String::compareTo);
        binarySearch = new BinarySearch(WORDS);
    }

    @Test
    void testSearch() {
        assertEquals(binarySearch.search("программа"), 24);
    }
}