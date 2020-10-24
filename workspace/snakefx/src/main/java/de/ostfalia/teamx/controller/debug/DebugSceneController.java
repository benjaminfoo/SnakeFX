package de.ostfalia.teamx.controller.debug;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DebugSceneController {

    @FXML Button login;
    @FXML Button register;
    @FXML Button homescreen;
    @FXML Button newgame;
    @FXML Button gamehistorie;
    @FXML Button gamescene;

    public void initialize () {
        login.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("login_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        register.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("register_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        homescreen.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("homescreen_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gamehistorie.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("gamehistory_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newgame.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("new_game_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gamescene.setOnAction(click -> {
            try {
                Stage window = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("gamecanvas_view.fxml")));
                window.setScene(scene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
