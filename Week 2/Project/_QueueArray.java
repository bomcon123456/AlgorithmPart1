/* *****************************************************************************
 *  Name: Trung Dao
 *  Date: 03/08
 *  Description: Queue implemented in array style
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

class QueueArray<Item> implements Iterable<Item> {

    Item[] m_items;
    int m_size;
    int m_head;
    int m_tail;

    public QueueArray() {
        m_items = (Item[]) (new Object[2]);
        m_size = 0;
        m_head = -1;
        m_tail = 0;
    }

    void resize(int capacity) {
        assert capacity >= m_size;
        Item[] temp = (Item[]) new Object[capacity];
        int count = 0;
        int cur = m_head;
        while (count < m_size) {
            if (m_items[cur] != null) {
                temp[count++] = m_items[cur];
            }
            cur = (++cur) % m_items.length;
        }
        m_tail = m_size;
        m_head = 0;
        m_items = temp;
    }

    void enqueue(Item item) {
        if (m_size == m_items.length) {
            resize(m_items.length * 2);
            //StdOut.println("        Resizing for Enqueue:" + m_items.length);
        }
        m_items[m_tail] = item;
        if (m_size == 0) {
            m_head = m_tail;
        }
        m_tail = (++m_tail) % m_items.length;
        m_size++;
    }

    Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        Item res = m_items[m_head];
        m_items[m_head] = null;
        m_head = (++m_head) % m_items.length;
        m_size--;
        if (m_size > 0 && m_size == (m_items.length / 4)) {
            resize(m_items.length / 2);
            //StdOut.println("        Resizing for Dequeue:" + m_items.length);
        }
        return res;
    }

    boolean isEmpty() {
        return (m_size == 0);
    }

    @Override
    public Iterator<Item> iterator() {
        return new MyQueueIterator();
    }


    private class MyQueueIterator implements Iterator<Item> {
        int m_cur;

        MyQueueIterator() {
            m_cur = 0;
        }

        @Override
        public boolean hasNext() {
            return m_cur < m_size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item res = m_items[m_cur++];
            return res;
        }
    }

    public static void main(String[] args) {
        QueueArray<Integer> test = new QueueArray<>();
        test.enqueue(50);
        test.enqueue(40);
        test.enqueue(30);
        test.enqueue(20);
        test.enqueue(10);
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        test.enqueue(10);
        test.enqueue(20);
        test.enqueue(30);
        test.enqueue(40);
        test.enqueue(50);
        test.enqueue(50);
        test.enqueue(60);
        test.enqueue(70);
        test.enqueue(80);
        test.enqueue(90);
        test.enqueue(100);
        test.enqueue(110);
        for (Integer i : test) {
            StdOut.println(i);
        }
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());
        // System.out.println(test.dequeue());

    }

}
