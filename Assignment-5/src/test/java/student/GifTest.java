package student;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class GifTest {
    Gif gif1 = new Gif("Computer Science Security GIF by Sandia National Labs", "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExMm1lNXRwNHJ1dzUxbTV6Mmc1anpncHh6Z3BoMDJ0Mmd3ZGdyemE1OCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RDZo7znAdn2u7sAcWH/giphy.gif");
    Gif gif2 = new Gif("Work From Home GIF by SalesForce", "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDhjZnA1eWpxZzlxM251NTJ2MHRvNmxhMmFldnYydTk1aTU1YWh4YiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/1GEATImIxEXVR79Dhk/giphy.gif");
    Gif gif3 = new Gif("Looney Tunes GIF by Looney Tunes World of Mayhem", "https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExcHdnYWI3bXM2cnB1MTRubjBydXZodXM1YmR3ZTdtYmRwYnNpZTduZSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RbDKaczqWovIugyJmW/giphy.gif");
    Gif gif4 = new Gif("Coding Computer Science GIF by XRay.Tech", "https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExNzhwb2R3cWZ3YXpjbG8wODJuc2s0cDF6ZnQzZmN1enJ1ejhnbHBueiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/QNFhOolVeCzPQ2Mx85/giphy.gif");

    Comparator<Gif> increasingGifNameComparator = Gif.INCREASING_NAME_NCOMPARATOR.getComparator();
    Comparator<Gif> decreasingGifNameComparator = Gif.DECREASING_NAME_NCOMPARATOR.getComparator();
    Comparator<Gif> increasingGifNameLengthComparator = Gif.INCREASING_NAME_LENGTH_NCOMPARATOR.getComparator();

    @Test
    public void testIncreasingGifNameComparator() {
        assertTrue(increasingGifNameComparator.compare(gif1, gif2) < 0);
        assertTrue(increasingGifNameComparator.compare(gif4, gif3) < 0);
        assertTrue(increasingGifNameComparator.compare(gif3, gif2) < 0);
    }

    @Test
    public void testDecreasingGifNameComparator() {
        assertTrue(decreasingGifNameComparator.compare(gif2, gif1) < 0);
        assertTrue(decreasingGifNameComparator.compare(gif3, gif4) < 0);
        assertTrue(decreasingGifNameComparator.compare(gif2, gif3) < 0);
    }

    @Test
    public void testIncreasingGifNameLengthComparator() {
        assertTrue(increasingGifNameLengthComparator.compare(gif2, gif1) < 0);
        assertTrue(increasingGifNameLengthComparator.compare(gif2, gif3) < 0);
        assertTrue(increasingGifNameLengthComparator.compare(gif4, gif3) < 0);
    }
}