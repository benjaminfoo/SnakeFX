package de.ostfalia.snakecore.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * Die Lobby enthält eine Liste von Spielrunden.
 */
public class Lobby {

    public List<Spielrunde> aktiveSpiele = new LinkedList<>();

    public List<Spieler> aktiveSpieler = new LinkedList<>();

}
