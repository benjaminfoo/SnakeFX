package de.ostfalia.teamx.model;

public class SpielDefinition {

    private String nameOfTheGame;
    private int numberOfPlayer;
    private int maxNumberOfPowerUps;
    private int mapWidth, mapHeight;
    private Spielregel spielregel;

    public SpielDefinition() {
    }

    public SpielDefinition(String nameOfTheGame, int numberOfPlayer, int maxNumberOfPowerUps, int mapWidth, int mapHeight, Spielregel spielregel) {
        this.nameOfTheGame = nameOfTheGame;
        this.numberOfPlayer = numberOfPlayer;
        this.maxNumberOfPowerUps = maxNumberOfPowerUps;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.spielregel = spielregel;
    }

    public String getNameOfTheGame() {
        return nameOfTheGame;
    }

    public void setNameOfTheGame(String nameOfTheGame) {
        this.nameOfTheGame = nameOfTheGame;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getMaxNumberOfPowerUps() {
        return maxNumberOfPowerUps;
    }

    public void setMaxNumberOfPowerUps(int maxNumberOfPowerUps) {
        this.maxNumberOfPowerUps = maxNumberOfPowerUps;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public Spielregel getSpielregel() {
        return spielregel;
    }

    public void setSpielregel(Spielregel spielregel) {
        this.spielregel = spielregel;
    }

    @Override
    public String toString() {
        return
            nameOfTheGame + " | Map-size: " + mapWidth + " x " + mapHeight + "\n"
            + "(x" + "/" + numberOfPlayer + ") " +" | Nr. of Powerups: " + maxNumberOfPowerUps;
    }
}
