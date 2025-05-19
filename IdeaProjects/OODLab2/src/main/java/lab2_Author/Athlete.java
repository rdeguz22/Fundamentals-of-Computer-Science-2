package lab2_Author;

import javax.lang.model.element.Name;
import java.util.Objects;

/**
 * Class lab2_Author.Athlete stores information about an athlete.
 **/
public class Athlete {

    protected Name athletesName;
    protected Double height;
    protected Double weight;
    protected String league;
    //private Match match;

    /**
     * Constructor for the class lab2_Author.Athlete.
     *
     * @param athletesName - String, representing athlete's name
     * @param height       - Double, representing athlete's height
     * @param weight       - Double, representing athlete's weight
     * @param league       - String, representing athlete's league
     **/
    public Athlete(Name athletesName, Double height, Double weight, String league) {
        this.athletesName = athletesName;
        this.height = height;
        this.weight = weight;
        this.league = league;
        // this is some comment
    }

    /**
     * Constructor for the class lab2_Author.Athlete.
     *
     * @param athletesName - String, representing athlete's name
     * @param height       - Double, representing athlete's height
     * @param weight       - Double, representing athlete's weight
     **/
    public Athlete(Name athletesName, Double height, Double weight) {
        this.athletesName = athletesName;
        this.height = height;
        this.weight = weight;
    }

    /**
     * @return - problem1.Name, giving athlete's name
     **/
    public Name getAthletesName() {
        return athletesName;

    }

    /**
     * @return - Double, giving athlete's height
     **/
    public Double getHeight() {
        return height;
    }

    /**
     * @return - Double, giving athlete's weight
     **/
    public Double getWeight() {
        return weight;
    }

    /**
     * @return - String, giving athlete's league
     **/
    public String getLeague() {
        return league;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Athlete athlete = (Athlete) o;
        return Objects.equals(athletesName, athlete.athletesName) && Objects.equals(
                height, athlete.height) && Objects.equals(weight, athlete.weight)
                && Objects.equals(league, athlete.league);
    }

    @Override
    public int hashCode() {
        return Objects.hash(athletesName, height, weight, league);
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "athletesName=" + athletesName +
                ", height=" + height +
                ", weight=" + weight +
                ", league='" + league + '\'' +
                '}';
    }
}