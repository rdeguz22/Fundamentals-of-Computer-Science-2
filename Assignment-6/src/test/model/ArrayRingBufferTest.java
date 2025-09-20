package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayRingBufferTest {
    private static final int CAPACITY = 3;

    private ArrayRingBuffer<Integer> buffer;

    @BeforeEach
    void setUp() {
        buffer = new ArrayRingBuffer(CAPACITY);
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
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);

        assertTrue(buffer.isFull());
        assertEquals(CAPACITY, buffer.size());
    }

    @Test
    void testEnqueueBeyondCapacity() {
        // Fill the buffer
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);
        assertTrue(buffer.isFull());

        // Overflow the buffer
        buffer.enqueue(4);  // Should overwrite 1 since it's oldest

        assertEquals(CAPACITY, buffer.size());
        assertEquals(2, buffer.peek());  // 2 is now the oldest value

        // Verify FIFO order of remaining elements
        assertEquals(2, buffer.dequeue());  // First out
        assertEquals(3, buffer.dequeue());  // Second out
        assertEquals(4, buffer.dequeue());  // Last out
        assertTrue(buffer.isEmpty());
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

        assertEquals(1, buffer.dequeue());
        assertEquals(2, buffer.dequeue());

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
        int size = buffer.size();

        assertEquals(1, buffer.peek());
        assertEquals(size, buffer.size());
        assertEquals(1, buffer.peek()); // Can peek multiple times
        assertEquals(1, buffer.dequeue()); // Value remains unchanged
    }

    @Test
    void testCircularBehavior() {
        // Fill the buffer
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);

        // Remove first element and add new one
        assertEquals(1, buffer.dequeue());
        buffer.enqueue(4);

        // Verify sequence of elements
        assertEquals(2, buffer.peek());
        assertEquals(2, buffer.dequeue());
        assertEquals(3, buffer.dequeue());
        assertEquals(4, buffer.dequeue());
    }

    @Test
    void testEnqueueNullValue() {
        buffer.enqueue(null);
        assertEquals(1, buffer.size());
        assertNull(buffer.peek());
        assertNull(buffer.dequeue());
    }

    @Test
    void testCompleteSequenceOfOperations() {
        // Fill buffer
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);
        assertEquals(1, buffer.peek());

        // Remove one and add one
        assertEquals(1, buffer.dequeue());
        buffer.enqueue(4);
        assertEquals(2, buffer.peek());

        // Remove another and add one
        assertEquals(2, buffer.dequeue());
        buffer.enqueue(5);
        assertEquals(3, buffer.peek());

        // Verify final sequence
        assertEquals(3, buffer.dequeue());
        assertEquals(4, buffer.dequeue());
        assertEquals(5, buffer.dequeue());
        assertTrue(buffer.isEmpty());
    }

    @Test
    void testOverwriteMultipleTimes() {
        // Fill buffer
        buffer.enqueue(1);
        buffer.enqueue(2);
        buffer.enqueue(3);

        // Overwrite entire buffer
        buffer.enqueue(4);
        buffer.enqueue(5);
        buffer.enqueue(6);

        // Verify sequence
        assertEquals(4, buffer.dequeue());
        assertEquals(5, buffer.dequeue());
        assertEquals(6, buffer.dequeue());
    }

    @Test
    void testPeekAfterMultipleOperations() {
        buffer.enqueue(1);
        assertEquals(1, buffer.peek());

        buffer.enqueue(2);
        assertEquals(1, buffer.peek());

        buffer.dequeue();
        assertEquals(2, buffer.peek());

        buffer.enqueue(3);
        assertEquals(2, buffer.peek());

        buffer.enqueue(4);
        assertEquals(2, buffer.peek());
    }

    @Test
    void testDrainingFilledBuffer() {
        ArrayRingBuffer<String> stringBuffer = new ArrayRingBuffer<>(CAPACITY, "hello");
        assertTrue(stringBuffer.isFull());
        assertFalse(stringBuffer.isEmpty());

        for (int size = CAPACITY; size > 0; size--) {
            assertEquals(size, stringBuffer.size());
            assertEquals(CAPACITY, stringBuffer.getCapacity());
            assertEquals("hello", stringBuffer.peek());
            assertEquals("hello", stringBuffer.dequeue());
        }

        assertFalse(stringBuffer.isFull());
        assertTrue(stringBuffer.isEmpty());
        assertThrows(NoSuchElementException.class, stringBuffer::peek);
        assertThrows(NoSuchElementException.class, stringBuffer::dequeue);
    }

    @Test
    void testOverwritingFilledBuffer() {
        ArrayRingBuffer<Integer> intBuffer = new ArrayRingBuffer<>(CAPACITY, 5);

        // Add ints in order.
        for (int i = 1; i <= CAPACITY; i++) {
            assertTrue(intBuffer.isFull());
            assertFalse(intBuffer.isEmpty());
            assertEquals(CAPACITY, intBuffer.size());
            intBuffer.enqueue(i);
        }

        // Test removing ints in order.
        for (int i = 1; i <= CAPACITY; i++) {
            assertEquals(i, intBuffer.peek());
            assertEquals(i, intBuffer.dequeue());
            assertEquals(CAPACITY - i, intBuffer.size());
        }
    }
}
