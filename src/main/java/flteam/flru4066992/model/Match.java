package flteam.flru4066992.model;

import java.util.Objects;

public class Match {

    private String league;
    private Team homeTeam;
    private Team awayTeam;
    private int time;

    public Match(String league, Team homeTeam, Team awayTeam, int time) {
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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
                ", time=" + time +
                '}';
    }

}
