package de.ostfalia.snakecore;

import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.UserConfig;
import de.ostfalia.snakecore.ws.client.StompClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppSnakeFX extends Application {

    public static Stage windowStage;

    private StompClient stompClient;

    private UserConfig userConfig;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception{

        // This is an ugly hack - it should get removed in a short time!
        windowStage = window;

        Parent root;

        // this loads the login-controller and the corresponding fxml
        //
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(Scenes.VIEW_LOGIN));
        root = fxmlLoader.load();

        // this loads the game-canvas controller and the corresponding fxml
        // root = FXMLLoader.load(getClass().getClassLoader().getResource("gamecanvas_view.fxml"));
        /*
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(Scenes.VIEW_DEBUG));
        root = fxmlLoader.load();
        */

        BaseController baseController = fxmlLoader.getController();
        baseController.currentStage = window;
        baseController.application = this;
        baseController.setTitle(ApplicationConstants.TITLE_DEBUG_CHOOSER);

        // setup the stage of the application
        /*
        window.setMinWidth(800);
        window.setMinHeight(600);
        */

        Scene current = window.getScene();
        double width = 800;
        double height = 600;
        if(current != null){
            width = window.getScene().getWidth();
            height = window.getScene().getHeight();
        }

        window.setScene(new Scene(root, width, height));
        window.setWidth(width);
        window.setHeight(height);

        // show the application stage
        window.show();
        // window.centerOnScreen();
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
