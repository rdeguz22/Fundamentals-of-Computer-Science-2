package student;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class PoemTest {
    Poem poem1 = new Poem("Sonnet 18 by William Shakespeare", "https://www.poetryfoundation.org/poems/45087/sonnet-18-shall-i-compare-thee-to-a-summers-day");
    Poem poem2 = new Poem("She Walks in Beauty by Lord Byron", "https://www.poetryfoundation.org/poems/43844/she-walks-in-beauty");
    Poem poem3 = new Poem("The Road Not Taken by Robert Frost", "https://www.poetryfoundation.org/poems/44272/the-road-not-taken");
    Poem poem4 = new Poem("Dreams by Langston Hughes", "https://www.poetryfoundation.org/poems/150995/dreams-5d767850da976");

    Comparator<Poem> increasingTitleComparator = Poem.INCREASING_TITLE_NCOMPARATOR.getComparator();
    Comparator<Poem> decreasingTitleComparator = Poem.DECREASING_TITLE_NCOMPARATOR.getComparator();
    Comparator<Poem> increasingTitleLengthComparator = Poem.INCREASING_TITLE_LENGTH_NCOMPARATOR.getComparator();

    @Test
    public void testIncreasingTitleComparator() {
        assertTrue(increasingTitleComparator.compare(poem1, poem3) < 0);
        assertTrue(increasingTitleComparator.compare(poem2, poem3) < 0);
        assertTrue(increasingTitleComparator.compare(poem4, poem2) < 0);
    }

    @Test
    public void testDecreasingTitleComparator() {
        assertTrue(decreasingTitleComparator.compare(poem3, poem1) < 0);
        assertTrue(decreasingTitleComparator.compare(poem3, poem2) < 0);
        assertTrue(decreasingTitleComparator.compare(poem2, poem4) < 0);
    }

    @Test
    public void testIncreasingTitleLengthComparator() {
        assertTrue(increasingTitleLengthComparator.compare(poem1, poem2) < 0);
        assertTrue(increasingTitleLengthComparator.compare(poem1, poem3) < 0);
        assertTrue(increasingTitleLengthComparator.compare(poem4, poem1) < 0);
    }

}