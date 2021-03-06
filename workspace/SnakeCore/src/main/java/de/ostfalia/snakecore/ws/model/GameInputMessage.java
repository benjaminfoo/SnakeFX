package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.RunningGame;

import java.util.Objects;

public class GameInputMessage {

    private String player;
    private String gameId;
    private String input;

    private boolean gameStarted;

    private RunningGame runningGame;

    public GameInputMessage() {
    }



    public GameInputMessage(String player, String gameId, String input, boolean gameStarted, RunningGame runningGame) {
        this.player = player;
        this.gameId = gameId;
        this.input = input;
        this.gameStarted = gameStarted;
        this.runningGame = runningGame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInputMessage that = (GameInputMessage) o;
        return gameStarted == that.gameStarted && Objects.equals(player, that.player) && Objects.equals(gameId, that.gameId) && Objects.equals(input, that.input) && Objects.equals(runningGame, that.runningGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, gameId, input, gameStarted, runningGame);
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public RunningGame getRunningGame() {
        return runningGame;
    }

    public void setRunningGame(RunningGame runningGame) {
        this.runningGame = runningGame;
    }
}