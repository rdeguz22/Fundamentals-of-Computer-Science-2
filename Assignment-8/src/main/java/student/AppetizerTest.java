package student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppetizerTest {
    Appetizer mixedFruit = new Appetizer("Mixed Fruit", 215);
    Appetizer mixedFruit1 = new Appetizer("Mixed Fruit", 215);
    Appetizer frenchFries = new Appetizer("French Fries", 275);

    @Test
    public void equalsTest() {
        assertEquals(mixedFruit, mixedFruit);
        assertEquals(mixedFruit, mixedFruit1);
        assertNotEquals(mixedFruit, frenchFries);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(mixedFruit.hashCode(), mixedFruit.hashCode());
        assertEquals(mixedFruit.hashCode(), mixedFruit1.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("Mixed Fruit ($2.15)", mixedFruit.toString());
        assertEquals("French Fries ($2.75)", frenchFries.toString());
    }
    
    @Test
    public void compareToTest() {
        assertTrue(frenchFries.compareTo(mixedFruit) < 0);
        assertEquals(0, mixedFruit1.compareTo(mixedFruit));
        assertTrue(mixedFruit.compareTo(frenchFries) > 0);
        assertTrue(new Appetizer("Coconut", 50).compareTo(new Appetizer("Coconut", 20)) > 0);
        assertTrue(new Appetizer("Coconut", 20).compareTo(new Appetizer("Coconut", 50)) < 0);
    }

}
