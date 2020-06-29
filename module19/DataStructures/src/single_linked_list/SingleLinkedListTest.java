package single_linked_list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SingleLinkedListTest {
    SingleLinkedList list;

    @BeforeEach
    void setUp() {
        list = new SingleLinkedList();
        ListItem item = new ListItem("top");
        list.push(item);
    }

    @Test
    void testPush() {
        ListItem item = new ListItem("testPush");
        list.push(item);
        assertEquals("testPush", list.pop().getData());
    }

    @Test
    void testPop() {
        ListItem item = new ListItem("testPop");
        list.push(item);
        assertEquals("testPop", list.pop().getData());
    }

    @Test
    void testRemoveTop() {
        ListItem item = new ListItem("testRemoveTop");
        ListItem item2 = new ListItem("testRemoveTop2");
        list.push(item);
        list.push(item2);
        list.removeTop();
        assertEquals("testRemoveTop", list.pop().getData());
    }

    @Test
    void testRemoveLast() {
        ListItem item = new ListItem("testRemoveLast");
        list.push(item);
        list.removeLast();
        assertNull(item.getNext());
    }
}