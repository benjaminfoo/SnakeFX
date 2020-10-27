package de.ostfalia.teamx.SnakeServer.model;

import javax.persistence.*;

/**
 * @author Benjamin Wulfert
 * The model for a player.
 */
@Entity
public class Spieler {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false, length = 255)
    public String email;

    @Column(nullable = false, length = 255)
    public String pass;

    public Spieler() {
    }

    public Spieler(long id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Spieler{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
