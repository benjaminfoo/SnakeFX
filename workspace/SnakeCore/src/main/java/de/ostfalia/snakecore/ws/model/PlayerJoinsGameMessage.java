package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;

import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * A message that indicates the joining of a player to an running game within the lobby.
 *
 */
public class PlayerJoinsGameMessage {

    public Spieler spieler;
    public RunningGame gameToJoin;
    public List<RunningGame> allGames;

    public PlayerJoinsGameMessage() {
    }

    public PlayerJoinsGameMessage(Spieler spieler, RunningGame gameToJoin, List<RunningGame> allGames) {
        this.spieler = spieler;
        this.gameToJoin = gameToJoin;
        this.allGames = allGames;
    }
}
