package bubble_sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bubble_sort.BubbleSort.sort;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BubbleSortTest {

    private final int[] sortArray = new int[100];

    @BeforeEach
    void setUp() {
        for (int i = 0; i < sortArray.length; i++) {
            sortArray[i] = (int) (Math.random() * 100);
        }
    }

    @Test
    void testSort() {
        boolean sortedByMinToMax = true;
        sort(sortArray);
        for (int i = 1; i < sortArray.length; i++) {
            if (sortArray[i - 1] > sortArray[i]) {
                sortedByMinToMax = false;
                break;
            }
        }
        assertTrue(sortedByMinToMax);
    }
}