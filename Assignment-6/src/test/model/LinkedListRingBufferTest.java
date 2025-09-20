package model;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListRingBufferTest extends RingBufferTest<Integer> {

    @Override
    protected IRingBuffer<Integer> createBuffer(int capacity) {
        return new LinkedListRingBuffer<>(capacity);
    }

    @Override
    protected IRingBuffer<Integer> createBuffer(int capacity, Integer value) {
        return new LinkedListRingBuffer<>(capacity, value);
    }


}