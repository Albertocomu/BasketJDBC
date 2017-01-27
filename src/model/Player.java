package model;

import java.time.LocalDate;

public class Player {

    public String name;
    public LocalDate birth;
    public String position;
    public int nbaskets;
    public int nrebounds;
    public int nassists;
    public Team team;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, LocalDate birth, String position, int nbaskets, int nrebounds, int nassists, Team team) {
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.nbaskets = nbaskets;
        this.nrebounds = nrebounds;
        this.nassists = nassists;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNbaskets() {
        return nbaskets;
    }

    public void setNbaskets(int nbaskets) {
        this.nbaskets = nbaskets;
    }

    public int getNrebounds() {
        return nrebounds;
    }

    public void setNrebounds(int nrebounds) {
        this.nrebounds = nrebounds;
    }

    public int getNassists() {
        return nassists;
    }

    public void setNassists(int nassists) {
        this.nassists = nassists;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "model.Player{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", position='" + position + '\'' +
                ", nbaskets=" + nbaskets +
                ", nrebounds=" + nrebounds +
                ", nassists=" + nassists +
                ", team=" + team +
                '}';
    }
}
