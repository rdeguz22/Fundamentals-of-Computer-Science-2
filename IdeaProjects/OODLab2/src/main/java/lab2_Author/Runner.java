package lab2_Author;

import javax.lang.model.element.Name;
import java.util.Objects;

public class Runner extends Athlete {

    private Double best5kTime;
    private Double bestHalfMarathonTime;
    private String favoriteRunningEvent;


    public Runner(Name athletesName, Double height, Double weight, String league, Double best5kTime, Double bestHalfMarathonTime, String favoriteRunningEvent) {
        super(athletesName, height, weight, league);
        this.best5kTime = best5kTime;
        this.bestHalfMarathonTime = bestHalfMarathonTime;
        this.favoriteRunningEvent = favoriteRunningEvent;
    }

    public Double getBest5kTime() {
        return best5kTime;
    }

    public Double getBestHalfMarathonTime() {
        return bestHalfMarathonTime;
    }

    public String getFavoriteRunningEvent() {
        return favoriteRunningEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Runner runner = (Runner) o;
        return Objects.equals(best5kTime, runner.best5kTime) && Objects.equals(bestHalfMarathonTime, runner.bestHalfMarathonTime) && Objects.equals(favoriteRunningEvent, runner.favoriteRunningEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), best5kTime, bestHalfMarathonTime, favoriteRunningEvent);
    }

    @Override
    public String toString() {
        return "Runner{" +
                "best5kTime=" + best5kTime +
                ", bestHalfMarathonTime=" + bestHalfMarathonTime +
                ", favoriteRunningEvent='" + favoriteRunningEvent + '\'' +
                '}';
    }
}
