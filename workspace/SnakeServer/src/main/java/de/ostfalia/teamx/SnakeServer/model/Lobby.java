package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjami Wulfert
 * Die Lobby enthält eine Liste von Spielrunden.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lobby {

    public List<Spielrunde> aktiveSpiele = new LinkedList<>();

    public List<Spieler> aktiveSpieler = new LinkedList<>();

}
