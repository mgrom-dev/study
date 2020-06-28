package binary_search;

import java.util.ArrayList;

public class BinarySearch
{
    private ArrayList<String> list;

    public BinarySearch(ArrayList<String> list)
    {
        this.list = list;
    }

    public int search(String query)
    {
        if (query == null || list == null || list.size() == 0) {
            return -1;
        }
        int from = 0, to = list.size() - 1;
        while(from <= to) {
            int mid = from + to >>> 1;
            int cmp = list.get(mid).compareTo(query);
            if (cmp < 0) {
                from = mid + 1;
            } else {
                if (cmp == 0) {
                    return mid;
                }
                to = mid - 1;
            }
        }
        return -1;
    }
}
