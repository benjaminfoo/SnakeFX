package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * @author Benjami Wulfert
 * Ein Spielstand enthält eine Map von Spielern inklusive ihrer Highscores
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Spielstand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToMany(mappedBy = "spielstand")
    public Set<SpielstandErgebnis> ergebnisse = new HashSet<>();

}
