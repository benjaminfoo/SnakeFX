package de.ostfalia.snakecore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Benjamin Wulfert
 * Ein Spielstand enthält eine Map von Spielern inklusive ihrer Highscores.
 * Ein Spielstand wird bei einem Spielende (Erfüllung einer Spielregel) erzeugt und in der Datenbank persistiert.
 */
@Entity
public class Spielstand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spielstand_id")
    public Long id;

    @Column
    public Long date;

    @JsonManagedReference
    @OneToMany(mappedBy = "spielstand", cascade = CascadeType.ALL)
    public Set<SpielstandErgebnis> ergebnisse = new HashSet<>();

    public Spielstand() {

    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SpielstandErgebnis> getErgebnisse() {
        return ergebnisse;
    }

    public void setErgebnisse(Set<SpielstandErgebnis> ergebnisse) {
        this.ergebnisse = ergebnisse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spielstand that = (Spielstand) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Spielstand{" +
                "id=" + id +
                '}';
    }
}
