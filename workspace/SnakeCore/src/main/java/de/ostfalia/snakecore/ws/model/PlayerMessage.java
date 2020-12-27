package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.Spieler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * A player message indicates that a player joined the server.
 *
 */
public class PlayerMessage {

    public Spieler newPlayer ;
    public List<Spieler> playersInLobby = new LinkedList<>();

    public PlayerMessage() {
    }

    public PlayerMessage(Spieler newPlayer) {
        this.newPlayer = newPlayer;
    }
}
