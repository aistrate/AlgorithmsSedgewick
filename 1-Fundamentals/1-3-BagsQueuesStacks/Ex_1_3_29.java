/*************************************************************************
 *
 *  A generic queue, implemented using a *circular* linked list.
 *  (Exercise 1.3.29) 
 *
 *  % java Ex_1_3_29 < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Ex_1_3_29<Item> implements Iterable<Item> {
    private int N;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

   /**
     * Create an empty queue.
     */
    public Ex_1_3_29() {
        last  = null;
    }

   /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return last == null;
    }

   /**
     * Return the number of items in the queue.
     */
    public int size() {
        return N;     
    }

   /**
     * Return the item least recently added to the queue.
     * Throw an exception if the queue is empty.
     */
    public Item peek() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        return last.next.item;
    }

   /**
     * Add the item to the queue.
     */
    public void enqueue(Item item) {
        Node x = new Node();
        x.item = item;
        if (isEmpty())
            x.next = x;
        else
        {
            x.next = last.next;
            last.next = x;
        }
        last = x;
        N++;
    }

   /**
     * Remove and return the item on the queue least recently added.
     * Throw an exception if the queue is empty.
     */
    public Item dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        Item item = last.next.item;
        if (last.next == last)
            last = null;
        else
            last.next = last.next.next;
        N--;
        return item;
    }

   /**
     * Return string representation.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 
 

   /**
     * Return an iterator that iterates over the items on the queue in FIFO order.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    private class ListIterator implements Iterator<Item> {
        private int n = N;
        private Node current = last;

        public boolean hasNext()  { return n > 0; }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.next.item;
            current = current.next;
            n--;
            return item;
        }
    }


    public static void main(String[] args) {
        Ex_1_3_29<String> q = new Ex_1_3_29<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue: [ " + q + "])");
    }
}
