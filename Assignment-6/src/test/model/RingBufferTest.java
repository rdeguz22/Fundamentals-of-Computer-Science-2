package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class RingBufferTest<T> {
    private static final int CAPACITY = 3;
    private IRingBuffer<Integer> buffer;

    protected abstract IRingBuffer<Integer> createBuffer(int capacity);

    protected abstract IRingBuffer<Integer> createBuffer(int capacity, T value);

    @BeforeEach
    void setUp() {
        buffer = createBuffer(CAPACITY);
    }

    @Test
    void testNewBufferIsEmpty() {
        assertTrue(buffer.isEmpty());
        assertEquals(0, buffer.size());
        assertEquals(CAPACITY, buffer.getCapacity());
        assertFalse(buffer.isFull());
    }

    @Test
    void testEnqueueIncreasesSize() {
        buffer.enqueue(1);
        assertEquals(1, buffer.size());
        assertFalse(buffer.isEmpty());
        assertFalse(buffer.isFull());
    }

    @Test
    void testEnqueueUntilFull() {
        for (int i = 0; i < CAPACITY; i++) {
            buffer.enqueue(1);
        }
        assertTrue(buffer.isFull());
        assertEquals(CAPACITY, buffer.size());
    }

    @Test
    void testDequeueRemovesElements() {
        buffer.enqueue(1);
        buffer.enqueue(2);
        assertEquals(1, buffer.dequeue());
        assertEquals(1, buffer.size());
        assertFalse(buffer.isEmpty());
        assertEquals(2, buffer.peek());
    }

    @Test
    void testDequeueUntilEmpty() {
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.dequeue();
        buffer.dequeue();
        assertTrue(buffer.isEmpty());
        assertEquals(0, buffer.size());
    }

    @Test
    void testDequeueOnEmptyBuffer() {
        assertThrows(NoSuchElementException.class, () -> buffer.dequeue());
    }

    @Test
    void testPeekOnEmptyBuffer() {
        assertThrows(NoSuchElementException.class, () -> buffer.peek());
    }

    @Test
    void testPeekDoesNotRemoveElement() {
        buffer.enqueue(1);
        assertEquals(1, buffer.peek());
        assertEquals(1, buffer.size());
    }
}
