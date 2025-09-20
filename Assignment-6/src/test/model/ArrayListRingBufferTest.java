package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListRingBufferTest extends RingBufferTest<Integer> {

    @Override
    protected IRingBuffer<Integer> createBuffer(int capacity) {
        return new ArrayRingBuffer<>(capacity);
    }

    @Override
    protected IRingBuffer<Integer> createBuffer(int capacity, Integer value) {
        return new ArrayListRingBuffer<>(capacity, value);
    }

}