package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class ArrayListRingBuffer<T> implements IRingBuffer<T> {
    private final List<T> buffer;
    private final int capacity;

    public ArrayListRingBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new ArrayList<>(capacity);
    }

    public ArrayListRingBuffer(int capacity, T value) {
        this.capacity = capacity;
        this.buffer = new ArrayList<>(capacity);
        this.buffer.add(value);
    }

    @Override
    public int size() {
        return buffer.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    @Override
    public boolean isFull() {
        return buffer.size() == capacity;
    }

    @Override
    public void enqueue(T value) {
        if (isFull()) {
            buffer.remove(0);
        }
        buffer.add(value);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Buffer is empty");
        }
        return buffer.remove(0);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Buffer is empty");
        }
        return buffer.get(0);
    }
}
