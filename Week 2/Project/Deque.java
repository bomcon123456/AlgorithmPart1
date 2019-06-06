/* *****************************************************************************
 *  Name: Trung Dao
 *  Date: 06/03/2019
 *  Description: Deque Class Implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item m_item;
        Node m_next;
        Node m_prev;

        Node(final Item item) {
            m_item = item;
            m_next = null;
            m_prev = null;
        }

        Node() {
            m_item = null;
            m_next = null;
            m_prev = null;
        }
    }

    private Node m_sentinelNode;
    private int m_size;

    public Deque() {
        m_sentinelNode = new Node(null);
        m_size = 0;
    }

    public boolean isEmpty() {
        return (m_size == 0);
    }

    public int size() {
        return m_size;
    }

    private Node getFront() {
        return m_sentinelNode.m_next;
    }

    private Node getBack() {
        return m_sentinelNode.m_prev;
    }

    private void addWhenEmpty(final Node node) {

        m_sentinelNode.m_next = node;
        m_sentinelNode.m_prev = node;
        m_size++;
    }

    private void validateAdd(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null arguments");
        }
    }

    private void validateRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove if deque's empty");
        }
    }

    public void addFirst(Item item) {
        validateAdd(item);
        Node insertee = new Node(item);
        if (isEmpty()) {
            addWhenEmpty(insertee);
            return;
        }
        Node curFront = getFront();

        insertee.m_prev = m_sentinelNode;
        insertee.m_next = curFront;
        curFront.m_prev = insertee;
        m_sentinelNode.m_next = insertee;

        m_size++;
    }

    public void addLast(Item item) {
        validateAdd(item);
        Node insertee = new Node(item);
        if (isEmpty()) {
            addWhenEmpty(insertee);
            return;
        }
        Node curBack = getBack();

        insertee.m_next = m_sentinelNode;
        insertee.m_prev = curBack;
        curBack.m_next = insertee;
        m_sentinelNode.m_prev = insertee;

        m_size++;
    }

    public Item removeFirst() {
        validateRemove();
        Node curFront = getFront();
        Item res = curFront.m_item;

        m_sentinelNode.m_next = curFront.m_next;
        if (curFront.m_next != null)
            curFront.m_next.m_prev = m_sentinelNode;
        if (m_size == 1) {
            m_sentinelNode.m_next = null;
            m_sentinelNode.m_prev = null;
        }
        curFront = null;

        m_size--;

        return res;
    }

    public Item removeLast() {
        validateRemove();
        Node curBack = getBack();
        Item res = curBack.m_item;

        m_sentinelNode.m_prev = curBack.m_prev;
        if (curBack.m_prev != null)
            curBack.m_prev.m_next = m_sentinelNode;
        if (m_size == 1) {
            m_sentinelNode.m_next = null;
            m_sentinelNode.m_prev = null;
        }
        curBack = null;

        m_size--;

        return res;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node m_current = getFront();

        @Override
        public boolean hasNext() {
            return ((m_current != m_sentinelNode) && (m_current != null));
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("End of the queue");
            }
            Item res = m_current.m_item;
            m_current = m_current.m_next;
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("We don't implement remove()");
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        test.addFirst(1);
        test.removeLast();
        for (Integer i : test) {
            StdOut.println(i);
        }
    }

}
