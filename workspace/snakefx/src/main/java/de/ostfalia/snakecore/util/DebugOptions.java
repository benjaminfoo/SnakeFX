package de.ostfalia.snakecore.util;

/**
 * @author Benjamin Wulfert
 *
 * Defines various options to allow easier debugging for developers.
 */
public class DebugOptions {

    // For debugging - Sets up a low stress game profile
    public static boolean DEBUG_SINGLEPLAYER = true;

    // For debugging - Sets up a low stress game profile
    public static boolean DEBUG_LOWCORE = false;

    // For debugging - Sets up a high stress game profile
    public static boolean DEBUG_HARDCORE = false;

    // For debugging - Sets up the npc movement - walking randomly within the map
    public static boolean DEBUG_NPC_MOVEMENT_RANDOM_PATHS = true;

    // For debugging - Sets up the npc movement - every npc just circles around
    public static boolean DEBUG_NPC_MOVEMENT_CIRCLING = false;

    // For debugging - Every player is an npc ( = not controlled by a player )
    public static boolean DEBUG_EVERYBODY_NPC = true;

    // For debugging - Draw the direction of the player <=> the next tile a player moves to
    public static boolean DEBUG_DRAW_DIRECTIONS = true;
    
}
