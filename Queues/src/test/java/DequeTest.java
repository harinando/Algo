import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by nando on 9/10/16.
 */
public class DequeTest {
    private Deque<Integer> deque;
    private int first = 1;
    private int second = 2;
    private int last = 3;
    private int beforeLast = 4;


    @Before
    public void setup() {
        deque = new Deque<Integer>();
    }

    private void createList() {
        deque.addLast(first);
        deque.addLast(second);
        deque.addLast(beforeLast);
        deque.addLast(last);
    }

    @Test
    public void testDequeIsEmpty() throws Exception {
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testDequeIsNotEmpty() throws Exception {
        deque.addFirst(first);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        deque.addFirst(first);
        assertEquals(1, deque.size());
    }

    @Test
    public void testAddFirst() throws Exception {
        int size = deque.size();
        deque.addFirst(first);
        assertEquals(++size, deque.size());
    }

    @Test
    public void testAddLast() throws Exception {
        int size = deque.size();
        deque.addLast(second);
        assertEquals(++size, deque.size());
    }

    @Test
    public void testRemoveFirst() throws Exception {
        createList();
        int size = deque.size();
        int item1 = deque.removeFirst();
        assertEquals(--size, deque.size());
        assertEquals(item1, first);

        int item2 = deque.removeFirst();
        assertEquals(--size, deque.size());
        assertEquals(item2, second);
    }

    @Test
    public void testRemoveLast() throws Exception {
        createList();
        int size = deque.size();
        int item1 = deque.removeLast();
        assertEquals(--size, deque.size());
        assertEquals(item1, last);

        int item2 = deque.removeLast();
        assertEquals(--size, deque.size());
        assertEquals(item2, beforeLast);
    }

    @Test
    public void testIterator() throws Exception {
        createList();
        List<Integer> output = Arrays.asList(first, second, beforeLast, last);
        int index = 0;
        for(Integer item : deque) {
            assertEquals(output.get(index++), item);
        }
    }

    @Test
    public void test(){
        deque.addLast(0); assertEquals(1, deque.size());
        assertFalse(deque.isEmpty());
        int item = deque.removeFirst();
        assertEquals(0, item);
        deque.addLast(3);
        deque.addLast(4);
        deque.isEmpty();
        deque.addLast(6);
        item = deque.removeFirst();
        assertEquals(3, item);
        item = deque.removeLast();
        assertEquals(6, item);
        deque.addFirst(0);
        item = deque.removeFirst();
        assertEquals(0, item);
    }
    @Test (expected = NullPointerException.class)
    public void addFirstShouldThrowNullPointerException() {
        deque.addFirst(null);
    }

    @Test (expected = NullPointerException.class)
    public void addLastShouldThrowNullPointerException() {
        deque.addLast(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void removeFirstShouldThrowNoSuchElementException() {
        deque.removeFirst();
    }

    @Test (expected = NoSuchElementException.class)
    public void removeLastShouldThrowNoSuchElementException() {
        deque.removeLast();
    }

    @Test (expected = NoSuchElementException.class)
    public void iteratorShouldThrowNoSuchElementException() {
        deque.iterator().next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void iteratorShouldThrowUnsupportedOperationException() {
        deque.iterator().remove();
    }
}