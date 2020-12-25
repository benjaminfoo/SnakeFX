package de.ostfalia.snakecore.ws.model;

public class GameInputMessage {

    private String player;
    private String gameId;
    private String input;

    public GameInputMessage() {
    }

    public GameInputMessage(String player, String gameId, String input) {
        this.player = player;
        this.gameId = gameId;
        this.input = input;
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

    @Override
    public String toString() {
        return "GameInputMessage{" +
                "player='" + player + '\'' +
                ", gameId='" + gameId + '\'' +
                ", input='" + input + '\'' +
                '}';
    }
}