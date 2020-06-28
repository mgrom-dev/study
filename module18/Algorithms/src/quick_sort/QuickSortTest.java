package quick_sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QuickSortTest {

    @Test
    void testSort() {
        int[] array = new int[]{7, 6, 4, 8, 12, 1, 3, 15};
        QuickSort.sort(array);
        assertArrayEquals(array, new int[]{1, 3, 4, 6, 7, 8, 12, 15});
    }
}