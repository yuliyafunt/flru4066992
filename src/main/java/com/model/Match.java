package com.model;

import java.time.LocalDateTime;

public class Match {

    private String league;
    private Team firstTeam;
    private Team secondTeam;
    private LocalDateTime matchTime;

    public Match(String league, Team firstTeam, Team secondTeam, LocalDateTime matchTime) {
        this.league = league;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.matchTime = matchTime;
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

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

}
