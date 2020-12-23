package de.ostfalia.snakecore.snakorino.model;

 // TODO: this needs to get connected with a SpielDefinition, i guess
public class Config {

    // Board configuration
    // These are constants of the game
    // maybe possible to use as XML to make configurable game values
    public final int width = 800;
    public final int height = width;
    public final int rows = 20;
    public final int columns = 20;
    public final int tileSize = width / rows;

}
