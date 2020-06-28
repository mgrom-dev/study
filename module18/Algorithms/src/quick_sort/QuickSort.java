package quick_sort;

public class QuickSort
{
    public static void sort(int[] array)
    {
        if(array.length <= 1) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int from, int to)
    {
        if(from < to)
        {
            int pivot = partition(array, from, to);
            sort(array, from, pivot - 1);
            sort(array, pivot + 1, to);
        }
    }

    private static int partition(int[] array, int from, int to)
    {
        int counter = from;
        while (from < to) {
            if (array[from] < array[to]) {
                int temp = array[counter];
                array[counter] = array[from];
                array[from] = temp;
                counter++;
            }
            from++;
        }
        int temp = array[to];
        array[to] = array[counter];
        array[counter] = temp;
        return counter;
    }
}
