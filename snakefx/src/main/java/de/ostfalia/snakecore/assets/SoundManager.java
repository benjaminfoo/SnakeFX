package de.ostfalia.snakecore.assets;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjamin Wulfert
 *
 * The SoundManager class handles loading and playing sound assets for the frontend of the game.
 *
 */
public class SoundManager {

    private static SoundManager instance; // well use a singleton for this because we only need on instance of this class ever per client.

    private String[] soundFileNames;

    private Map<String, AudioClip> audioClipCache;

    public SoundManager(){
        soundFileNames = new String[]{
                "explosion.wav",
                "hit_1.wav",
                "hit_2.wav",
                "pickup.wav",
                "pickup_2.wav",
                "powerup_1.wav",
                "powerup_2.wav",
                "powerup_3.wav",
                "select_1.wav",
                "select_2.wav",
                "move.wav",
        };

        audioClipCache = new HashMap<>();

        for (String soundFileName : soundFileNames) {
            initSound(soundFileName);
        }
    }

    /**
     * Initialize a sound - put it into the sound cache
     * @param fileName The sound to initialize
     */
    private void initSound(String fileName){
        final URL resource = getClass().getClassLoader().getResource("sounds/" + fileName);
        final AudioClip clip = new AudioClip(resource.toString());

        // play the sound once so it gets loaded into media query of javafx
        clip.play(0);

        audioClipCache.put(fileName, clip);
    }

    // Singleton management nothing special
    public static SoundManager getInstance() {
        if(instance == null){
            instance = new SoundManager();
        }
        return instance;
    }

    // Simple util methods to play initialized sounds
    public void playExplosion(){ audioClipCache.get("explosion.wav").play(); }
    public void playHit(){ audioClipCache.get("hit_1.wav").play(); }
    public void playHit2(){ audioClipCache.get("hit_2.wav").play(); }
    public void playPickup(){ audioClipCache.get("pickup.wav").play(); }
    public void playPickup2(){ audioClipCache.get("pickup_2.wav").play(); }
    public void playPowerup1(){ audioClipCache.get("powerup_1.wav").play(); }
    public void playPowerup2(){ audioClipCache.get("powerup_2.wav").play(); }
    public void playPowerup3(){ audioClipCache.get("powerup_3.wav").play(); }
    public void playSelect1(){ audioClipCache.get("select_1.wav").play(); }

    public void playSelect2() {
        audioClipCache.get("select_2.wav").play();
    }

    public void playMove() {
        audioClipCache.get("move.wav").play();
    }

}
