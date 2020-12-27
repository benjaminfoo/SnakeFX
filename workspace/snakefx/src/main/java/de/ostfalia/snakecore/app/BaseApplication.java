package de.ostfalia.snakecore.app;

import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.UserConfig;
import de.ostfalia.snakecore.ws.client.StompClient;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Benjamin Wulfert
 * The baseApplication is the parent class for the snakefx-client.
 * Its the configuration class for the client and holds references to the stompClient, the UserConfig, etc.
 */
public abstract class BaseApplication extends Application {

    // the stage which gets reused through-out the application's lifecycle
    private Stage windowStage;

    // the STOMP-Client for the communication with the backend over websockets
    private StompClient stompClient;

    // the users details which gets used for identification later on and holds the JSON-Web-Token
    private UserConfig userConfig;

    // the startup parameters used when the application is called (for example from public static void main -> args[])
    private List<String> startParams;

    public Map<Class, BaseController> initializedController = new HashMap<>();

    // is the application executed in debug-mode?
    public static boolean inDebugMode = false;

    // Local user management
    public UserConfig getUserConfig(){ return userConfig; }
    public void setUserConfig(UserConfig userConfig) { this.userConfig = userConfig; }

    // Communication via STOMP
    public StompClient getStompClient() {

        if(stompClient == null){
            stompClient = new StompClient();
        }

        return stompClient;
    }

    public Stage getWindowStage() {
        return windowStage;
    }

    public static boolean isInDebugMode() {
        return inDebugMode;
    }

    public void setWindowStage(Stage windowStage) {
        this.windowStage = windowStage;
    }

    public void setStompClient(StompClient stompClient) {
        this.stompClient = stompClient;
    }

    public static void setInDebugMode(boolean inDebugMode) {
        BaseApplication.inDebugMode = inDebugMode;
    }

    public List<String> getStartParams() {
        return startParams;
    }

    public void setStartParams(List<String> startParams) {
        this.startParams = startParams;
    }

    public Spieler getUserConfigAsSpieler(){
        return new Spieler(-1337L, userConfig.getUserName(), userConfig.getPass());
    }
}
