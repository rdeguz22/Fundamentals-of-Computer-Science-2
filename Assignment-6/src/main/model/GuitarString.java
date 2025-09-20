package model;

import java.util.Random;

/**
 * A simulated guitar string.
 */
public class GuitarString {
    private static final int SAMPLING_RATE = 44_100;
    private static final double ENERGY_DECAY_FACTOR = .994;

    private final IRingBuffer<Double> buffer;
    private final Random random = new Random();

    /**
     * Creates a simulated guitar string with the specified frequency.
     *
     * @param frequency the frequency, in Hertz
     */
    public GuitarString(double frequency) {
        // Initialize buffer by calling the two-argument constructor
        // of either ArrayListRingBuffer or LinkedListRingBuffer.
        // The capacity of the buffer should be the sampling rate
        // (defined above) divided by the frequency. The initial value
        // should be 0.0.
        int capacity = (int) Math.round(SAMPLING_RATE / frequency);
        buffer = new ArrayListRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(0.0);
        }
    }

    /**
     * Plucks this string.
     */
    public void pluck() {
        // Fill the buffer (replacing any previous values) with random
        // numbers in the range [-0.5, +.5).
        for (int i = 0; i < buffer.getCapacity(); i++) {
            buffer.enqueue(random.nextDouble() - 0.5);
        }
    }

    /**
     * Enqueues a sample of this guitar string.
     */
    public void tick() {
        // Add a new sample to the buffer, overwriting the oldest:
        // 1. Read and dequeue the first sample from the buffer.
        // 2. Read (but do not dequeue) the next sample.
        // 3. Calculate the average of the two samples.
        // 4. Multiply it by the decay factor (defined above).
        // 5. Enqueue the result in the buffer.
        double firstSample = buffer.dequeue();
        double secondSample = buffer.peek();
        double newSample = (firstSample + secondSample) / 2.0 * ENERGY_DECAY_FACTOR;
        buffer.enqueue(newSample);
    }

    /**
     * Gets the next sample of this guitar string.
     *
     * @return a sample
     */
    public double sample() {
        // Return (but do not dequeue) the next sample.
        return buffer.peek();
    }
}
