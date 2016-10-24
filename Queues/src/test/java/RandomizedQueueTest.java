import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nando on 9/17/16.
 */
public class RandomizedQueueTest {

    private RandomizedQueue<Integer> queue;
    @Before
    public void setup() {
        queue = new RandomizedQueue<Integer>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(2, queue.size());
    }

    @Test
    public void testArrayShouldResize() throws Exception {
        int max_size = 20;
        for (int i=0; i < max_size; i++) {
            queue.enqueue(i);
        }
        assertEquals(20, queue.size());
    }

    @Test
    public void testEnqueue() throws Exception {
        queue.enqueue(1);
        assertEquals(1, queue.size());
    }

    @Test
    public void testDequeue() throws Exception {
        queue.enqueue(1);
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSample() throws Exception {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        int x = queue.sample();
        List<Integer> result = Arrays.asList(1, 2, 3);
        assertTrue(result.contains(x));
    }

    @Test (expected = NoSuchElementException.class)
    public void testShouldThrowNoSuchElementException() {
        queue.dequeue();
    }

    @Test (expected = NoSuchElementException.class)
    public void dequeueShouldThrowNoSuchElementException() {
        queue.enqueue(1);
        queue.dequeue();
        queue.dequeue();
    }

    @Test (expected = NoSuchElementException.class)
    public void sampleShouldThrowNoSuchElementException() {
        queue.sample();
    }

    @Test (expected = NoSuchElementException.class)
    public void iteratorShouldThrowNoSuchElementException() {
        queue.iterator().next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testShouldThrowUnsupportedOperationException() {
        queue.iterator().remove();
    }

    private void createList(int max) {
        for(int i=0; i<max; i++) {
            queue.enqueue(i);
        }
    }

    @Test
    public void testIterator() throws Exception {
        HashSet<Integer> set = new HashSet<Integer>();
        boolean hasDuplicate = false;
        createList(10);
        for(int x : queue) {
            if (set.contains(x)) {
                hasDuplicate = true;
            }
        }
        assertFalse(hasDuplicate);
    }

    /*
    *     {  1, 2, 3, 4, 5, 6, 7, 8, 9, 10  }
    *
     */
    @Test
    public void testIteratorOnDelete() {
        createList(10);
        int y = queue.dequeue();
        y = queue.dequeue();
        queue.enqueue(11);
        y = queue.dequeue();
        for(int x : queue) {
            System.out.println(x);
        }
    }

    @Test (expected = NullPointerException.class)
    public void enqueueFirstShouldThrowNullPointerException() {
            queue.enqueue(null);
    }

    @Test
    public void test() {
        System.out.println("****************");
        queue.enqueue(49);
        queue.enqueue(11);
        System.out.println(queue.dequeue());    // 49
        System.out.println(queue.isEmpty());    // false
        System.out.println(queue.dequeue());    // 11
        queue.enqueue(2);
    }
}