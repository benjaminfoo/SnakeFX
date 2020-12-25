package de.ostfalia.snakecore;

import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.UserConfig;
import de.ostfalia.snakecore.ws.client.StompClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * @author Benjamin Wulfert
 * The entry point of the javafx-application.
 */
public class AppSnakeFX extends Application {

    // the stage which gets reused through-out the application's lifecycle
    private Stage windowStage;

    // the STOMP-Client for the communication with the backend over websockets
    private StompClient stompClient;

    // the users details which gets used for identification later on and holds the JSON-Web-Token
    private UserConfig userConfig;

    // plain old psvm - the entry point of the application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        // safe the reference to the current window - we'll safe it for future uses
        windowStage = window;

        // this loads the login-controller and the corresponding fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(Scenes.VIEW_LOGIN));
        Parent root = fxmlLoader.load();

        BaseController baseController = fxmlLoader.getController();
        baseController.currentStage = window;
        baseController.application = this;
        baseController.currentStage.getIcons().add(new Image("icon.png"));
        baseController.setTitle(ApplicationConstants.TITLE_LOGIN);

        // setup the stage of the application
        Scene current = window.getScene();
        double width = 1024;
        double height = 768;
        if(current != null){
            width = window.getScene().getWidth();
            height = window.getScene().getHeight();
        }

        window.setScene(new Scene(root, width, height));
        window.setWidth(width);
        window.setHeight(height);

        // show the application stage
        window.show();

        // if the user presses F12 on his keyboard, the debug scene-viewer gets called and displayed
        window.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F12){
                baseController.showLayoutInNewWindow(Scenes.VIEW_DEBUG, ApplicationConstants.TITLE_DEBUG_CHOOSER);
            }
        });
    }

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
}
