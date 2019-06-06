/* ****************************************************************************
 *  Name: Trung Dao
 *  Date: 03/08
 *  Description: Randomized Queue Implementation
 *****************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] m_items;
    private int m_size;


    public RandomizedQueue() {
        m_items = (Item[]) new Object[2];
        m_size = 0;
    }

    public boolean isEmpty() {
        return (m_size == 0);
    }

    public int size() {
        return m_size;
    }

    private void swapArrayPos(int start, int des) {
        Item temp = m_items[start];
        m_items[start] = m_items[des];
        m_items[des] = temp;
    }

    private void swapToEnd(int index) {
        swapArrayPos(index, m_size - 1);
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Insertee can't be null.");
        }
        if (m_size == m_items.length) {
            resize(m_items.length * 2);
        }
        m_items[m_size++] = item;
    }

    private void resize(int capacity) {
        assert capacity >= m_size;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < m_size; i++) {
            temp[i] = m_items[i];
        }
        m_items = temp;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniform(0, m_size);
        Item res = m_items[index];
        swapToEnd(index);
        m_items[m_size - 1] = null;
        m_size--;

        if (m_size > 0 && m_size == m_items.length / 4) {
            resize(m_items.length / 2);
        }

        return res;
    }

    // return but not kick out
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return m_items[StdRandom.uniform(0, m_size)];
    }

    @Override

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {
        int[] order;
        int cur;

        MyIterator() {
            cur = 0;
            order = new int[m_size];
            for (int i = 0; i < m_size; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported.");
        }

        @Override
        public boolean hasNext() {
            return (cur != order.length);
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Can't find next element.");
            Item res = m_items[order[cur++]];
            return res;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        test.enqueue(50);
        test.enqueue(40);
        test.enqueue(30);
        test.enqueue(20);
        test.enqueue(10);
        StdOut.println(test.size());
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
        int z = 0;
        while (z < 3) {
            StdOut.println("Printing");
            for (Integer i : test) {
                StdOut.println(i);
            }
            z++;
        }
    }

}
