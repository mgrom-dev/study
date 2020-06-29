package single_linked_list;

public class SingleLinkedList
{
    private ListItem top;

    public void push(ListItem item)
    {
        if(top != null) {
            item.setNext(top);
        }
        top = item;
    }

    public ListItem pop()
    {
        ListItem item = top;
        if(top != null)
        {
            top = top.getNext();
            item.setNext(null);
        }
        return item;
    }

    public void removeTop()
    {
        if(top != null) {
            top = top.getNext();
        }
    }

    public void removeLast()
    {
        if (top != null) {
            ListItem item = top;
            while (item.getNext() != null && item.getNext().getNext() != null) {
                item = item.getNext();
            }
            item.setNext(null);
        }
    }
}