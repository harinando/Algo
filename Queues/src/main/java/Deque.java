import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node pre, post;
    private int n;

    public Deque() {
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
        n = 0;
    }

    public boolean isEmpty() {  return n == 0; }

    public int size() {
        return n;
    }

    /*
     * pre <-> first <-> second    # Inserting x at the front
     * pre <-> x <-> first <-> second
     */
    public void addFirst(Item item) {
        if (item == null) { throw new NullPointerException(); }
        Node first = pre.next;
        Node x = new Node();
        x.item = item;
        x.prev = pre;
        x.next = first;
        pre.next = x;
        first.prev = x;
        n++;
    }

    /*
     *   before_last <-> last <-> post      # Insert x at the back
      *  before_last <-> last <-> x <-> post
     */
    public void addLast(Item item) {
        if (item == null) { throw new NullPointerException(); }
        Node last = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        n++;
    }

    /*
    *       pre <-> first <-> second
    *       pre <-> second
    *
    *       pre <-> post
    *
    *       pre <-> first <-> post
     */
    public Item removeFirst() {
        if (pre.next == post) { throw new NoSuchElementException(); }
        Node oldFirst = pre.next;
        Node first = oldFirst.next;
        Item item = oldFirst.item;
        pre.next = first;
        first.prev = pre;
        n--;
        return item;
    }

    /*
    *       before_last <-> last <-> post
    *       before_last <-> post
     */
    public Item removeLast() {
        if (pre.next == post) { throw new NoSuchElementException(); }
        Node last = post.prev;
        Node beforeLast = last.prev;
        Item item = last.item;
        beforeLast.next = post;
        post.prev = beforeLast;
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = pre.next;
        private int index = 0;

        public boolean hasNext()  { return index < n;                           }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }
    }


    public static void main(String[] args) {
    }
}
