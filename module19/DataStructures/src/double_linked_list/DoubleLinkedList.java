package double_linked_list;

public class DoubleLinkedList
{
    private ListItem head;
    private ListItem tail;

    public ListItem getHeadElement()
    {
        return head;
    }

    public ListItem getTailElement()
    {
        return tail;
    }

    public ListItem popHeadElement()
    {
        ListItem item = head;
        removeHeadElement();
        return item;
    }

    public ListItem popTailElement()
    {
        ListItem item = tail;
        removeTailElement();
        return item;
    }

    public void removeHeadElement()
    {
        if(head != null) {
            head = head.getPrev();
            if (head != null) {
                head.setNext(null);
            } else {
                tail = null;
            }
        }
    }

    public void removeTailElement()
    {
        if(tail != null) {
            tail = tail.getNext();
            if (tail != null) {
                tail.setPrev(null);
            } else {
                head = null;
            }
        }
    }

    public void addToHead(ListItem item)
    {
        if(head != null) {
            item.setPrev(head);
            head.setNext(item);
        }
        if (tail == null) {
            tail = item;
        }
        head = item;
    }

    public void addToTail(ListItem item)
    {
        if(tail != null) {
            item.setNext(tail);
            tail.setPrev(item);
        }
        if (head == null) {
            head = item;
        }
        tail = item;
    }
}