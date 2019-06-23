package com.model;

public class Team {

    private String name;
    private int score;
    private double coefficient;

    public Team(String name, int score, double coefficient) {
        this.name = name;
        this.score = score;
        this.coefficient = coefficient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", coefficient=" + coefficient +
                '}';
    }

}
