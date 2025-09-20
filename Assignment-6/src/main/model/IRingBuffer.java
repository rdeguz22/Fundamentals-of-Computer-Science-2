package model;

import java.util.*;

public interface IRingBuffer<T> {
    /**
     * Gets the number of elements in this ring buffer.
     *
     * @return the number of elements
     */
    int size();

    /**
     * Gets the capacity of this ring buffer.
     *
     * @return the capacity
     */
    int getCapacity();

    /**
     * Checks whether this ring buffer is empty.
     *
     * @return true if it is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Checks whether this ring buffer is full.
     *
     * @return true if it is full, false otherwise
     */
    boolean isFull();

    /**
     * Enqueues a value into this ring buffer. If this buffer is full,
     * this overwrites the oldest value.
     *
     * @param value the value
     */
    void enqueue(T value);

    /**
     * Dequeues the oldest value in this ring buffer.
     *
     * @return the value
     * @throws NoSuchElementException if the ring buffer is empty
     */
    T dequeue();

    /**
     * Reads the oldest value from this ring buffer without removing it.
     *
     * @return the value
     * @throws NoSuchElementException if the ring buffer is empty
     */
    T peek();
}
