package de.ostfalia.snakecore.app;

import de.ostfalia.snakecore.assets.SoundManager;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.UserConfig;
import de.ostfalia.snakecore.ws.client.StompClient;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Benjamin Wulfert
 * The baseApplication is the parent class for the snakefx-client.
 * Its the configuration class for the client and holds references to the stompClient, the UserConfig, etc.
 */
public abstract class BaseApplication extends Application {

    public BaseApplication() {
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        stompClient = new StompClient();
        soundManager.getInstance(); // initialize the soundmanagers assets by making a first call to the instance
    }

    // the stage which gets reused through-out the application's lifecycle
    private Stage windowStage;

    // the STOMP-Client for the communication with the backend over websockets
    private StompClient stompClient;

    // the users details which gets used for identification later on and holds the JSON-Web-Token
    private UserConfig userConfig;

    // the spieler object instance, used for communicating marshalled object instances
    private Spieler spieler;

    // the startup parameters used when the application is called (for example from public static void main -> args[])
    private List<String> startParams;

    // a class <-> controller map which acts as a cache for application controller
    public Map<Class, BaseController> initializedController = new HashMap<>();

    // is the application executed in debug-mode?
    public static boolean inDebugMode = false;

    // a simple date formatter in order to display formated dates or time value
    private SimpleDateFormat simpleDateFormat;

    // the manager for handling the playback of sounds
    private SoundManager soundManager;


    //
    // GETTERS AND SETTERS
    //

    // Local user management
    public UserConfig getUserConfig(){ return userConfig; }
    public void setUserConfig(UserConfig userConfig) { this.userConfig = userConfig; }

    public SoundManager getSoundManager(){ return soundManager.getInstance(); }

    public BaseApplication(Spieler spieler) {
        this.spieler = spieler;
    }

    public Stage getWindowStage() {
        return windowStage;
    }

    public void setWindowStage(Stage windowStage) {
        this.windowStage = windowStage;
    }

    public StompClient getStompClient() {
        return stompClient;
    }

    public void setStompClient(StompClient stompClient) {
        this.stompClient = stompClient;
    }

    public List<String> getStartParams() {
        return startParams;
    }

    public void setStartParams(List<String> startParams) {
        this.startParams = startParams;
    }

    public Map<Class, BaseController> getInitializedController() {
        return initializedController;
    }

    public void setInitializedController(Map<Class, BaseController> initializedController) {
        this.initializedController = initializedController;
    }

    public static boolean isInDebugMode() {
        return inDebugMode;
    }

    public static void setInDebugMode(boolean inDebugMode) {
        BaseApplication.inDebugMode = inDebugMode;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public Spieler getSpieler() {
        if(spieler == null){
            this.spieler = new Spieler(userConfig.getUserName(), userConfig.getPass());
        }
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }
}
