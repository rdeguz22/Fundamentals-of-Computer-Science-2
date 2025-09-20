package model;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A ring buffer implemented using an array.
 *
 * @param <T> the type of data stored in the buffer
 */
public class ArrayRingBuffer<T> implements IRingBuffer<T> {
    private final T[] buffer;
    private int oldest;
    private int newest;
    private int size;

    /**
     * Constructs an empty ring buffer with the specified capacity.
     *
     * @param capacity the capacity
     */
    public ArrayRingBuffer(int capacity) {
        buffer = (T[]) new Object[capacity];
        oldest = -1;
        newest = -1;
        size = 0;
    }

    /**
     * Constructs a ring buffer with the specified capacity and all
     * elements initialized to the specified value.
     *
     * @param capacity the capacity
     * @param value    the initial value
     */
    public ArrayRingBuffer(int capacity, T value) {
        buffer = (T[]) new Object[capacity];
        Arrays.fill(buffer, value);
        oldest = 0;
        newest = capacity - 1;
        size = capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getCapacity() {
        return buffer.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size() == buffer.length;
    }

    @Override
    public void enqueue(T value) {
        newest = (newest + 1) % getCapacity();
        buffer[newest] = value;
        if (oldest == -1) {
            oldest = newest;
        } else if (newest == oldest) {
            oldest = (oldest + 1) % getCapacity();
        }
        if (size < getCapacity()) {
            size++;
        }
    }

    @Override
    public T dequeue() {
        T result = peek();
        oldest = (oldest + 1) % buffer.length;
        size--;
        return result;
    }

    @Override
    public T peek() {
        if (size() == 0) {
            throw new NoSuchElementException("Cannot dequeue from an empty main.model.array.RingBuffer");
        }
        return buffer[oldest];
    }
}
