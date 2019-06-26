package flteam.flru4066992.model;

import flteam.flru4066992.model.time.Time;

import java.util.Objects;
import java.util.Optional;

public class Match {

    private String league;
    private Team homeTeam;
    private Team awayTeam;
    private Time time;

    public Match(String league, Team homeTeam, Team awayTeam, Time time) {
        this.league = league;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * ATTENTION: time field not participate in equals and hashcode
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(league, match.league) &&
                Objects.equals(homeTeam, match.homeTeam) &&
                Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(league, homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return "Match{" +
                "league='" + league + '\'' +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", time=" + Optional.ofNullable(time).map(Time::getTime).orElse(null) +
                '}';
    }

}
