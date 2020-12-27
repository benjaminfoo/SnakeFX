package de.ostfalia.snakecore.ws.model;

public class GameInputMessage {

    private String player;
    private String gameId;
    private String input;

    private boolean gameStarted;

    public GameInputMessage() {
    }

    public GameInputMessage(String player, String gameId, String input, boolean gameStarted) {
        this.player = player;
        this.gameId = gameId;
        this.input = input;
        this.gameStarted = gameStarted;
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

    @Override
    public String toString() {
        return "GameInputMessage{" +
                "player='" + player + '\'' +
                ", gameId='" + gameId + '\'' +
                ", input='" + input + '\'' +
                ", gameStarted=" + gameStarted +
                '}';
    }
}