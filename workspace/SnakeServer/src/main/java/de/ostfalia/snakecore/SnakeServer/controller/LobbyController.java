package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;

@Controller
public class LobbyController {

    private List<Spieler> currentPlayers = new LinkedList<>();

    private List<RunningGame> runningGames = new LinkedList<>();

    public LobbyController() {
    }

    public LobbyController(List<RunningGame> runningGames) {
        this.runningGames = runningGames;
    }

    public boolean add(RunningGame runningGame) {
        return runningGames.add(runningGame);
    }

    public boolean remove(Object o) {
        return runningGames.remove(o);
    }

    public List<RunningGame> getRunningGames() {
        return runningGames;
    }

    public List<Spieler> getCurrentPlayers() {
        return currentPlayers;
    }
}
