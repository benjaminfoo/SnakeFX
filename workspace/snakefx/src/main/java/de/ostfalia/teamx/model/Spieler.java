package de.ostfalia.teamx.model;

public class Spieler {

    public long id;
    public String name;
    public String pass;

    public Spieler(long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public Spieler() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Spieler{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
