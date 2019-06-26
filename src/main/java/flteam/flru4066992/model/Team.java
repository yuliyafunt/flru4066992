package flteam.flru4066992.model;

import java.util.Objects;

public class Team {

    private String name;
    private Integer score;
    private Double coefficient;

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

    /**
     * ATTENTION: score, coefficient fields not participate in equals and hashcode
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
