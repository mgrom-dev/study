package double_linked_list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DoubleLinkedListTest {
    DoubleLinkedList list;

    @BeforeEach
    void setUp() {
        list = new DoubleLinkedList();
        ListItem head = new ListItem("head");
        ListItem tail = new ListItem("tail");
        list.addToHead(head);
        list.addToTail(tail);
    }

    @Test
    void getHeadElement() {
        assertEquals("head", list.getHeadElement().getData());
        assertEquals("tail", list.getHeadElement().getPrev().getData());
    }

    @Test
    void getTailElement() {
        assertEquals("tail", list.getTailElement().getData());
        assertEquals("head", list.getTailElement().getNext().getData());
    }

    @Test
    void popHeadElement() {
        ListItem item = new ListItem("testPopHead");
        list.addToHead(item);
        assertEquals("testPopHead", list.popHeadElement().getData());
        assertEquals("head", list.getHeadElement().getData());
        assertEquals("tail", list.getHeadElement().getPrev().getData());
        assertNull(list.getHeadElement().getNext());
    }

    @Test
    void popTailElement() {
        ListItem item = new ListItem("testPopTail");
        list.addToTail(item);
        assertEquals("testPopTail", list.popTailElement().getData());
        assertEquals("tail", list.getTailElement().getData());
        assertEquals("head", list.getTailElement().getNext().getData());
        assertNull(list.getTailElement().getPrev());
    }

    @Test
    void removeHeadElement() {
        ListItem item = new ListItem("testRemoveHead");
        ListItem item2 = new ListItem("deletedItem");
        list.addToHead(item);
        list.addToHead(item2);
        list.removeHeadElement();
        assertEquals("testRemoveHead", list.getHeadElement().getData());
        list.removeHeadElement();
        assertEquals("head", list.getHeadElement().getData());
    }

    @Test
    void removeTailElement() {
        ListItem item = new ListItem("testRemoveTail");
        ListItem item2 = new ListItem("deletedItem");
        list.addToTail(item);
        list.addToTail(item2);
        list.removeTailElement();
        assertEquals("testRemoveTail", list.getTailElement().getData());
        list.removeTailElement();
        assertEquals("tail", list.getTailElement().getData());
    }

    @Test
    void addToHead() {
        ListItem item = new ListItem("testAddToHead");
        list.addToHead(item);
        assertEquals("testAddToHead", list.getHeadElement().getData());
    }

    @Test
    void addToTail() {
        ListItem item = new ListItem("testAddToTail");
        list.addToTail(item);
        assertEquals("testAddToTail", list.getTailElement().getData());
    }
}