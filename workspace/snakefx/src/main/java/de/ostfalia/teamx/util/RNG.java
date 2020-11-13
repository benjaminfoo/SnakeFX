package de.ostfalia.teamx.util;

import java.util.Random;

/**
 * @author Benjamin Wulfert
 * Util for generating random numbers (rng = random number generator).
 * This class uses the singleton pattern.
 */
public class RNG {

    private Random random;

    private static RNG instance = new RNG();

    private RNG(){
        random = new Random();
    }

    public static RNG getInstance(){
        return instance;
    }

    /**
     * Generates a random number
     * @param min - the minimum number which gets generated - inclusive = can be a part of the generation
     * @param max - the maximum number which gets generated - exclusive = is not a part of the generation
     * @return - the generated number
     */
    public int generate(int min, int max){
        return random.nextInt(max - min) + min;
    }

}
