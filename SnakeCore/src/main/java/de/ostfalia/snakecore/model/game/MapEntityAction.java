package de.ostfalia.snakecore.model.game;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;

/**
 * @author Benjamin Wulfert
 *
 * Callback-Interface for executing code when an mapEntity has been used / reached / activated / touched by a player.
 */

public interface MapEntityAction {

    public void onExecute(Spieler spieler, Snake snake, RunningGame runningGame);

}
