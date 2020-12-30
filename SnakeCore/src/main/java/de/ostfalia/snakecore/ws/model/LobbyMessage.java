package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spieler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * A lobby message indicates a new game when send to the backend.
 * Contains a list of all currently running games when send to the frontend.
 *
 */
public class LobbyMessage {

    public Spieler admin;
    public SpielDefinition spielDefinition;
    public List<RunningGame> runningGames = new LinkedList<>();

    public LobbyMessage() {
    }

    public LobbyMessage(Spieler admin, SpielDefinition spielDefinition) {
        this.admin = admin;
        this.spielDefinition = spielDefinition;
    }
}
