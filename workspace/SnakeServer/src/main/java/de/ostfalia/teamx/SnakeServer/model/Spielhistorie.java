package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 * Eine Spielhistorie enthält eine Liste von Spielständen.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spielhistorie {

    List<Spielstand> spielstaende = new LinkedList<>();

}
