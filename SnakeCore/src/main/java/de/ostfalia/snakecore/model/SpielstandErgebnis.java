package de.ostfalia.snakecore.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Benjamin Wulfert
 * <p>
 * Ein SpielstandErgebnis enthält einen Spieler und dessen erreichten Score beim Ende einer Spielrunde.
 * Ein SpielstandErgebnis ist Teil eines Spielstands.
 */
@Entity
public class SpielstandErgebnis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spielstandergebnis_id")
    public Long id;

    public Integer score;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spielstand_id", nullable = false)
    public Spielstand spielstand;

    @OneToOne
    public Spieler spieler;

    public SpielstandErgebnis() {
    }

    public SpielstandErgebnis(Integer score, Spieler spieler) {
        this.score = score;
        this.spieler = spieler;
    }

    public SpielstandErgebnis(Long id, Integer score, Spieler spieler) {
        this.id = id;
        this.score = score;
        this.spieler = spieler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpielstandErgebnis ergebnis = (SpielstandErgebnis) o;
        return Objects.equals(id, ergebnis.id) && Objects.equals(score, ergebnis.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }

    @Override
    public String toString() {
        return "SpielstandErgebnis{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}
