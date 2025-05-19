package lab2_Author;

import javax.lang.model.element.Name;
import java.util.Objects;

public class BaseballPlayer extends Athlete {

    private String team;
    private Double avgBatting;
    private Integer seasonHomeRuns;


    public BaseballPlayer(Name athletesName, Double height, Double weight, String league, String team, Double avgBatting, Integer seasonHomeRuns) {
        super(athletesName, height, weight, league);
        this.team = team;
        this.avgBatting = avgBatting;
        this.seasonHomeRuns = seasonHomeRuns;
    }

    public String getTeam() {
        return team;
    }

    public Double getAvgBatting() {
        return avgBatting;
    }

    public Integer getSeasonHomeRuns() {
        return seasonHomeRuns;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseballPlayer that = (BaseballPlayer) o;
        return Objects.equals(team, that.team) && Objects.equals(avgBatting, that.avgBatting) && Objects.equals(seasonHomeRuns, that.seasonHomeRuns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, avgBatting, seasonHomeRuns);
    }

    @Override
    public String toString() {
        return "BaseballPlayer{" +
                "team='" + team + '\'' +
                ", avgBatting=" + avgBatting +
                ", seasonHomeRuns=" + seasonHomeRuns +
                '}';
    }
}
