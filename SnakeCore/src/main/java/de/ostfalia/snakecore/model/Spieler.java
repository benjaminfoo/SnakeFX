package de.ostfalia.snakecore.model;

import javax.persistence.*;

/**
 * @author Benjami Wulfert
 *
 * Modell-Klasse für einen Spieler - ein Spieler wird über seinen Namen und sein Passwort definiert.
 *
 */
@Entity
public class Spieler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 255)
    public String name;

    @Column(nullable = false, length = 255)
    public String pass;

    public Spieler() {
    }

    public Spieler(String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spieler spieler = (Spieler) o;

        if (name != null ? !name.equals(spieler.name) : spieler.name != null) return false;
        return pass != null ? pass.equals(spieler.pass) : spieler.pass == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

}
