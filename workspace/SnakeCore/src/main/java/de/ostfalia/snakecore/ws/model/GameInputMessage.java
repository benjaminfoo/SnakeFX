package de.ostfalia.snakecore.ws.model;

public class GameInputMessage {

    private String player;
    private String input;

    public GameInputMessage() {
    }

    public GameInputMessage(String playerName, String input) {
        this.player = playerName;
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}