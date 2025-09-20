package student;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {
    private static final Appetizer MIXED_FRUIT = new Appetizer("Mixed Fruit", 215);
    private static final Appetizer FRENCH_FRIES = new Appetizer("French Fries", 275);
    private static final Appetizer SIDE_SALAD = new Appetizer("Side Salad", 335);
    private static final Appetizer HOT_WINGS = new Appetizer("Hot Wings", 355);
    private static final Appetizer MOZZARELLA_STICKS = new Appetizer("Mozzarella Sticks", 420);
    private static final Appetizer SAMPLER_PLATE = new Appetizer("Sampler Plate", 580);
    private static final List<Appetizer> XKCD_APPETIZERS = List.of(
            MIXED_FRUIT, FRENCH_FRIES, SIDE_SALAD, HOT_WINGS, MOZZARELLA_STICKS, SAMPLER_PLATE
    );
    private static final int XKCD_PRICE = 1505;

    // These lists are sorted and should be compared to lists that are sorted.
    private static final List<Appetizer> XKCD_SOLUTION1 = List.of(
            MIXED_FRUIT, MIXED_FRUIT, MIXED_FRUIT, MIXED_FRUIT, MIXED_FRUIT, MIXED_FRUIT, MIXED_FRUIT);
    private static final List<Appetizer> XKCD_SOLUTION2 = List.of(
            HOT_WINGS, HOT_WINGS, MIXED_FRUIT, SAMPLER_PLATE
    );
    private static final Set<List<Appetizer>> XKCD_SOLUTIONS = Set.of(XKCD_SOLUTION1, XKCD_SOLUTION2);

    private int getTotalPrice(List<Appetizer> appetizers) {
        // TODO: Return the sum of the prices.
        return 0;
    }

    @Test
    public void findOneSolutionWorksForXkcdData() {
        Solver solver = new Solver(XKCD_APPETIZERS);
        List<Appetizer> actual = solver.findOneSolution(XKCD_PRICE);

        // Verify that a solution was returned.
        assertNotNull(actual);

        // Make sure its price is right.
        assertEquals(XKCD_PRICE, getTotalPrice(actual));

        // Sort the solution so it is easy to compare to the (sorted) expected
        // solutions.
        Collections.sort(actual);

        // Make sure it equals one of the two expected solutions.
        assertTrue(XKCD_SOLUTION1.equals(actual) || XKCD_SOLUTION2.equals(actual));
    }

    // Asserts that every element in actualSolutions, when sorted, is in
    // expectedSolutions, and vice versa.
    private void checkSolutions(Set<List<Appetizer>> expectedSolutions, List<List<Appetizer>> actualSolutions) {
        Set<List<Appetizer>> uniqueSortedActualSolutions = new HashSet<>();
        for (List<Appetizer> actualSolution : actualSolutions) {
            Collections.sort(actualSolution);
            uniqueSortedActualSolutions.add(actualSolution);
        }

        assertEquals(expectedSolutions.size(), uniqueSortedActualSolutions.size(),
                "size of expected solutions differs from size of actual solution");
        for (List<Appetizer> solution : uniqueSortedActualSolutions) {
            assertTrue(expectedSolutions.contains(solution),
                    String.format(
                            "The set of expected solutions (%s) did not contain the " +
                                    "sorted actual solution %s", expectedSolutions, solution));
        }
    }

    @Test
    public void findAllSolutionsWorksForXkcdData() {
        Solver solver = new Solver(XKCD_APPETIZERS);
        List<List<Appetizer>> actual = solver.findAllSolutions(XKCD_PRICE);
        checkSolutions(XKCD_SOLUTIONS, actual);
    }
}
