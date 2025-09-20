package student;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeowTest {
    // 4 instances of Meow for testing
    Meow meow1 = new Meow("ANI01", 101);
    Meow meow2 = new Meow("CAN01", 202);
    Meow meow3 = new Meow("JJX01", 301);
    Meow meow4 = new Meow("BLE01", 307);

    Comparator<Meow> increasingCatIdComparator = Meow.INCREASING_CAT_ID_NCOMPARATOR.getComparator();
    Comparator<Meow> increasingRecordingIdComparator = Meow.INCREASING_RECORDING_ID_NCOMPARATOR.getComparator();
    Comparator<Meow> decreasingCatIdComparator = Meow.DECREASING_CAT_ID_NCOMPARATOR.getComparator();

    @Test
    public void testIncreasingCatIdComparator() {
        assertTrue(increasingCatIdComparator.compare(meow1, meow4) < 0);
        assertTrue(increasingCatIdComparator.compare(meow2, meow3) < 0);
        assertTrue(increasingCatIdComparator.compare(meow1, meow2) < 0);
    }

    @Test
    public void testIncreasingRecordingIdComparator() {
        assertTrue(increasingRecordingIdComparator.compare(meow1, meow2) < 0);
        assertTrue(increasingRecordingIdComparator.compare(meow2, meow3) < 0);
        assertTrue(increasingRecordingIdComparator.compare(meow3, meow4) < 0);
    }

    @Test
    public void TestDecreasingCatIdComparator() {
        assertTrue(decreasingCatIdComparator.compare(meow4, meow1) < 0);
        assertTrue(decreasingCatIdComparator.compare(meow3, meow2) < 0);
        assertTrue(decreasingCatIdComparator.compare(meow2, meow1) < 0);
    }
}