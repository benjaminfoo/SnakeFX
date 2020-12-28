package de.ostfalia.snakecore.snakorino.model;

 // TODO: this needs to get connected with a SpielDefinition, i guess
public class Config {

    // Board configuration
    // These are constants of the game
    // maybe possible to use as XML to make configurable game values
    public int width = 800;
    public int height = width;
    public int rows = 20;
    public int columns = 20;
    public int tileSize = width / rows;

}
