package com.model;

public class Match {

    private String league;
    private Team firstTeam;
    private Team secondTeam;
    private String time;

    public Match(String league, Team firstTeam, Team secondTeam, String time) {
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Match{" +
                "league='" + league + '\'' +
                ", firstTeam=" + firstTeam +
                ", secondTeam=" + secondTeam +
                ", time=" + time +
                '}';
    }

}
