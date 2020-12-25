package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.RunningGame;

import java.util.LinkedList;
import java.util.List;

public class LobbyMessage {

    public List<RunningGame> runningGames = new LinkedList<>();

    public LobbyMessage() {
    }

    public LobbyMessage(List<RunningGame> runningGames) {
        this.runningGames = runningGames;
    }
}
