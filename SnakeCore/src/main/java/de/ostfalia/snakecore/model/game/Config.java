package de.ostfalia.snakecore.model.game;

 // TODO: this needs to get connected with a SpielDefinition, i guess
/**
 * @author Leonard Reidel
 * The Config class is a model which contains information for running games on the client side. (like resolution, etc.)
 */
public class Config {

    // Board configuration
    // These are constants of the game
    // maybe possible to use as XML to make configurable game values
    public int width = 640;
    public int height = width;
    public int rows = 20;
    public int columns = 20;
    public int tileSize = width / rows;

     public Config(int rows, int columns) {
         this.rows = rows;
         this.columns = columns;
     }

     public Config() {
     }
 }
