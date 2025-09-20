package model;

import java.util.NoSuchElementException;

/**
 * A ring buffer implemented using a linked list.
 *
 * @param <T> the type of data stored in the buffer
 */
public class LinkedListRingBuffer<T> implements IRingBuffer<T> {
    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int capacity;

    public LinkedListRingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public LinkedListRingBuffer(int capacity, T value) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.head = new Node<>(value, null);
        this.tail = this.head;
        this.tail.next = this.head;
        this.size = 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public void enqueue(T value) {
        Node<T> node = new Node<>(value, null);
        if (isEmpty()) {
            head = node;
            tail = node;
            tail.next = head;
        } else if (size < capacity) {
            tail.next = node;
            tail = node;
            tail.next = head;
        } else {
            head.value = value;
            head = head.next;
            tail = tail.next;
        }

        if (size < capacity) {
            size++;
        }
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty ring buffer.");
        }
        T value = head.value;
        head = head.next;
        size--;
        return value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek an empty ring buffer.");
        }
        return head.value;
    }
}