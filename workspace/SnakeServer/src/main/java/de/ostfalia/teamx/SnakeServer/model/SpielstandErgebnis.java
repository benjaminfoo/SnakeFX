package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjamin Wulfert
 * Ein SpielstandErgebnis enthält einen Spieler und dessen erreichten Score beim Ende einer Spielrunde.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpielstandErgebnis {

    public SpielstandErgebnis(Spieler spieler, Integer score) {
        this.spieler = spieler;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Integer score;

    @ManyToOne
    public Spieler spieler;

    @ManyToOne
    @JoinColumn(name="Spielstand_id", nullable=false)
    public Spielstand spielstand;

}
