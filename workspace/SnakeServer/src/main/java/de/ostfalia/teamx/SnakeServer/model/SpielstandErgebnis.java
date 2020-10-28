package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjami Wulfert
 * Ein SpielstandErgebnis enthält einen Spieler und dessen erreichten Score beim Ende einer Spielrunde.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SpielstandErgebnis {

    public SpielstandErgebnis(long id, Spieler spieler, Integer score) {
        this.id = id;
        this.spieler = spieler;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    public Spieler spieler;

    @ManyToOne
    public Spielstand spielstand;

}
