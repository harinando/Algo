import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] container;
    private int n, tail, head;

    public RandomizedQueue() {
        container = (Item[]) new Object[2];
        n = 0;
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) { throw new NullPointerException(); }
        if (head+n == container.length) {
            resize(2*container.length);    // double size of array if necessary
        }
        container[tail++] = item;
        n++;
    }

    public Item dequeue() {
        if (n == 0) { throw new NoSuchElementException(); }
        int idx = getNext();
        swap(idx, head);
        Item first = container[head];
        container[head] = null;
        head++;
        n--;
        return first;
    }

    public Item sample() {
        if (n == 0) { throw new NoSuchElementException(); }
        return container[getNext()];
    }

    public Iterator<Item> iterator() {
        return new RandomisedListIterator();
    }

    /*
    *   [ null, null, 1, 2, 3, 4, 5, 6 ]        # Resize array to 2*6 and insert 7
    *   [ 1, 2, 3, 4, 5, 6, 7 , null, null, null, null, null ]
    *   PRE-CONDITION: Starts with NULL.
     */
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = container[head+i];
        }
        container = temp;
        head = 0;
        tail = n;
    }

    /*
    *
    *
     */
    private class RandomisedListIterator implements Iterator<Item> {
        private Item[] shuffledContainer;
        private int index;

        private RandomisedListIterator() {
            shuffledContainer = (Item[]) new Object[n];
            index = 0;
            for (int i = 0; i < n; i++) {
                shuffledContainer[i] = container[i+head];
            }
            StdRandom.shuffle(shuffledContainer);
        }

        public boolean hasNext()  { return index < n;                           }

        public void remove()      { throw new UnsupportedOperationException();  }


        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = shuffledContainer[index++];
            return item;
        }
    }

    /*
    *   [ null, null, head, elt1, elt2, elt3, tail, null, null ]  # getNext() => 4
    *   Should return elt4
     */
    private int getNext() {
        return head + StdRandom.uniform(n);
    }

    private void swap(int i, int j) {
        Item x = container[j];
        container[j] = container[i];
        container[i] = x;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(49);
        queue.enqueue(11);
        System.out.println(queue.dequeue());    // 49
        System.out.println(queue.isEmpty());    // false
        System.out.println(queue.dequeue());    // 11
        queue.enqueue(2);
        System.out.println(queue.sample());
    }
}