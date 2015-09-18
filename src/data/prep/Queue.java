package data.prep;

// Created by mrtyormaa on 9/17/15.

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private Node head;
    private Node last;
    private int N = 0;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) head = last;
        else oldlast.next = last;
        N++;
    }

    public Item dequeue() {
        Item item = head.item;
        head = head.next;
        N--;
        if (isEmpty()) last = null;
        return item;
    }

    public Item fetchTail() {
        return last.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

    }
}
