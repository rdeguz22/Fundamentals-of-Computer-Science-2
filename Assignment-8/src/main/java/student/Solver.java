package student;

import java.util.ArrayList;
import java.util.List;

/**
 * A solver that can determine sets of appetizers that can be purchased
 * for a specified price.
 */
public class Solver {
    private final List<Appetizer> appetizers;
    private List<List<Appetizer>> solutions;

    /**
     * Creates a solver for the provided appetizers.
     *
     * @param appetizers the appetizers
     */
    public Solver(List<Appetizer> appetizers) {
        this.appetizers = appetizers;
    }

    /**
     * Finds a single list of appetizers that can be purchased for
     * the specified price.
     *
     * @param totalPrice the total price
     * @return a single solution, or null if no solution exists
     */
    public List<Appetizer> findOneSolution(int totalPrice) {
        List<Appetizer> currentSelection = new ArrayList<>();
        if (findSolutionHelper(totalPrice, 0, currentSelection)) {
            return currentSelection;
        }
        return null;
    }

    /**
     * Helper method for findOneSolution().
     *
     * @param remainingPrice   the remaining price to reach
     * @param startIndex       the starting index in our available appetizers list
     * @param currentSelection the current selection of appetizers
     * @return true if a solution is found, false otherwise
     */
    private boolean findSolutionHelper(int remainingPrice, int startIndex,
                                       List<Appetizer> currentSelection) {
        if (remainingPrice == 0) {
            return true;
        }
        if (remainingPrice < 0) {
            return false;
        }
        List<Appetizer> availableAppetizers = Solver.this.appetizers;
        for (int i = startIndex; i < availableAppetizers.size(); i++) {
            Appetizer current = availableAppetizers.get(i);
            if (current.getPrice() > remainingPrice) {
                continue;
            }
            currentSelection.add(current);
            if (findSolutionHelper(remainingPrice - current.getPrice(), i, currentSelection)) {
                return true;
            }
            currentSelection.remove(currentSelection.size() - 1);
        }
        return false;
    }


    /**
     * Finds all lists of appetizers that can be purchased for
     * the specified price.
     *
     * @param totalPrice the price in pennies
     * @return the solutions
     */
    public List<List<Appetizer>> findAllSolutions(int totalPrice) {
        List<List<Appetizer>> solutions = new ArrayList<>();
        List<Appetizer> currentSelection = new ArrayList<>();
        findAllSolutionsHelper(totalPrice, 0, currentSelection, solutions);
        return solutions;
    }

    /**
     * Helper method for findAllSolutions(int totalPrice).
     *
     * @param remainingPrice   the remaining price to reach
     * @param startIndex       the starting index in our available appetizers list
     * @param currentSelection the current selection of appetizers
     * @param solutions        the list to collect all valid allSolutions
     */
    private void findAllSolutionsHelper(int remainingPrice, int startIndex,
                                        List<Appetizer> currentSelection,
                                        List<List<Appetizer>> solutions) {
        if (remainingPrice == 0) {
            solutions.add(new ArrayList<>(currentSelection));
            return;
        }
        if (remainingPrice < 0) {
            return;
        }
        List<Appetizer> availableAppetizers = Solver.this.appetizers;
        for (int i = startIndex; i < availableAppetizers.size(); i++) {
            Appetizer current = availableAppetizers.get(i);
            if (current.getPrice() > remainingPrice) {
                continue;
            }
            currentSelection.add(current);
            findAllSolutionsHelper(remainingPrice - current.getPrice(),
                    i, currentSelection, solutions);
            currentSelection.remove(currentSelection.size() - 1);
        }
    }
}
