package de.ostfalia.snakecore.ws.model;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.SpielstandErgebnis;
import de.ostfalia.snakecore.model.game.Config;
import de.ostfalia.snakecore.pattern.MapEntity;
import de.ostfalia.snakecore.model.game.Snake;
import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Benjamin Wulfert
 *
 * A GameSessionMessage is a container which contains any information about a currently running game.
 *
 */
public class GameSessionMessage {


    // an enum with the different stages of the game
    public enum GameState {
        STARTING,
        RUNNING,
        FINISHING;
    }

    // whom send the message?
    private Spieler sender;

    // which game is this message targeted at?
    private String gameId;

    // Reference to the game in the lobby -> active player can be identified with this
    private RunningGame runningGame;

    // related to game state
    private GameState gameState;

    // related to player input
    private KeyCode input;

    // related to map-entities
    public boolean entityConsumed;
    public Set<MapEntity> mapEntities;
    public int amountOfFoodDrawables;

    public Collection<Snake> snakeList;
    public Config config;

    public Snake spielerSnake;

    public SpielstandErgebnis winnerResult;
    public List<SpielstandErgebnis> loserResults;

    public GameSessionMessage() {

    }


    /**
     * This constructor is used to indicate a started game session
     */
    public GameSessionMessage(GameState gameState, Spieler userName, RunningGame runningGame) {
        this.gameState = gameState;
        this.sender = userName;
        this.runningGame = runningGame;
    }


    /**
     * This constructor is used to generate new food / powerup positions
     */
    public GameSessionMessage(GameState gameState, String nameOfTheGame, Collection<Snake> snakeList, Config config, boolean entityConsumed) {
        this.gameState = gameState;
        this.gameId = nameOfTheGame;
        this.snakeList = snakeList;
        this.config = config;
        this.entityConsumed = entityConsumed;
    }

    /**
     * This constructor is used to exchange movement information of clients.
     */
    public GameSessionMessage(GameState gameState, Spieler playerName, String gameId, KeyCode input) {
        this.gameState = gameState;
        this.gameId = gameId;
        this.sender = playerName;
        this.input = input;
    }


    public Spieler getSender() {
        return sender;
    }

    public void setSender(Spieler sender) {
        this.sender = sender;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public RunningGame getRunningGame() {
        return runningGame;
    }

    public void setRunningGame(RunningGame runningGame) {
        this.runningGame = runningGame;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public KeyCode getInput() {
        return input;
    }

    public void setInput(KeyCode input) {
        this.input = input;
    }

    public boolean isEntityConsumed() {
        return entityConsumed;
    }

    public void setEntityConsumed(boolean entityConsumed) {
        this.entityConsumed = entityConsumed;
    }

    public Set<MapEntity> getEntities() {
        return mapEntities;
    }

    public void setFoods(Set<MapEntity> mapEntities) {
        this.mapEntities = mapEntities;
    }

    public Collection<Snake> getSnakeList() {
        return snakeList;
    }

    public void setSnakeList(List<Snake> snakeList) {
        this.snakeList = snakeList;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSessionMessage that = (GameSessionMessage) o;
        return entityConsumed == that.entityConsumed && Objects.equals(sender, that.sender) && Objects.equals(gameId, that.gameId) && Objects.equals(runningGame, that.runningGame) && gameState == that.gameState && input == that.input;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, gameId, runningGame, gameState, input, entityConsumed);
    }

    @Override
    public String toString() {
        return "GameSessionMessage{" +
                "player='" + sender + '\'' +
                ", gameId='" + gameId + '\'' +
                ", runningGame=" + runningGame +
                ", gameState=" + gameState +
                ", input=" + input +
                '}';
    }
}