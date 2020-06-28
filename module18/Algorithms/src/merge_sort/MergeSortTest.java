package merge_sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    @Test
    void testMergeSort() {
        int[] array = new int[]{7, 6, 4, 8, 12, 1, 3, 15};
        MergeSort.mergeSort(array);
        assertArrayEquals(array, new int[]{1, 3, 4, 6, 7, 8, 12, 15});
    }
}