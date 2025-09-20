package student;

import java.util.Objects;

import static java.lang.Integer.compare;

/**
 * An appetizer that can be ordered in any quantity.
 */
public class Appetizer implements Comparable<Appetizer> {
    private static final double CENTS_PER_DOLLAR = 100.0;
    private String name;
    private int price; // pennies

    /**
     * Creates an appetizer with the specified name and price.
     *
     * @param name  the name
     * @param price the price in pennies
     */
    public Appetizer(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the name of this appetizer.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of this appetizer.
     *
     * @return the price in pennies
     */
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " ($" + (price / CENTS_PER_DOLLAR) + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Appetizer) {
            return this.name.equals(((Appetizer) other).name)
                    && this.price == ((Appetizer) other).price;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public int compareTo(Appetizer other) {
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }
        return compare(this.price, other.price);
    }
}
