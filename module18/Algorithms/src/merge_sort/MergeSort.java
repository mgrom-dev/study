package merge_sort;

import quick_sort.QuickSort;

public class MergeSort
{
    public static void mergeSort(int[] array)
    {
        int n = array.length;
        int middle = n / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[n - middle];

        System.arraycopy(array, 0, leftArray, 0, middle);
        System.arraycopy(array, middle, rightArray, 0, n - middle);
        QuickSort.sort(leftArray);
        QuickSort.sort(rightArray);

        merge(array, leftArray, rightArray);
    }

    private static void merge(int[] array, int[] left, int[] right)
    {
        for (int i = 0, x = 0, y = 0; i < array.length; i++){
            array[i] = (x >= left.length ? right[y++] : (y >= right.length ? left[x++] : (left[x] < right[y] ? left[x++] : right[y++])));
        }
    }
}
