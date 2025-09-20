// Students should not modify this file.
package student;

import java.util.Comparator;

/**
 * An encapsulation of a comparator and a descriptive name.
 *
 * @param <T> the type of the items being compared
 */
public class NamedComparator<T> {
    private String name;
    private Comparator<T> comparator;

    /**
     * Constructs the named comparator.
     *
     * @param name       the name
     * @param comparator the comparator
     */
    public NamedComparator(String name, Comparator<T> comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the comparator.
     *
     * @return the comparator
     */
    public Comparator<T> getComparator() {
        return comparator;
    }
}
