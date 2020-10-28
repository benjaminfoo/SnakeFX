package de.ostfalia.teamx.SnakeServer.model;

import javax.persistence.*;

@Entity
public class Spieler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, length = 255)
    public String name;

    @Column(nullable = false, length = 255)
    public String pass;

    public Spieler() {
    }

    public Spieler(long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Spieler{" +
                "id=" + id +
                ", email='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
