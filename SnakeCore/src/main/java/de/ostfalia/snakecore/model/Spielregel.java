package de.ostfalia.snakecore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Benjamin Wulfert
 *
 * Eine Spielregel stellt eine Aussage dar welche erfüllt werden muss um ein Spiel zu beenden (z.B. Erreichung einer bestimmten Punktzahl).
 */
@Entity
public class Spielregel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Type {
        HIGHSCORE_10,
        HIGHSCORE_50,
        HIGHSCORE_100,
    }

    public String name;
    public Type type;

    public Spielregel() {
    }

    public Spielregel(String name) {
        this.name = name;
    }

    public Spielregel(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Regel: " + name;
    }
}
