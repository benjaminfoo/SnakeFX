package de.ostfalia.teamx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppSnakeFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage windowStage;

    @Override
    public void start(Stage window) throws Exception{

        // This is an ugly hack - it should get removed in a short time!
        windowStage = window;

        Parent root;

        // this loads the login-controller and the corresponding fxml
        // root = FXMLLoader.load(getClass().getClassLoader().getResource("login_view.fxml"));

        // this loads the game-canvas controller and the corresponding fxml
        // root = FXMLLoader.load(getClass().getClassLoader().getResource("gamecanvas_view.fxml"));
        root = FXMLLoader.load(getClass().getClassLoader().getResource("debug_scenechooser.fxml"));

        // setup the stage of the application
        window.setTitle("SnakeFX");
        window.setMinWidth(300);
        window.setMinHeight(400);
        window.setScene(new Scene(root));

        // show the application stage
        window.show();

    }

}
