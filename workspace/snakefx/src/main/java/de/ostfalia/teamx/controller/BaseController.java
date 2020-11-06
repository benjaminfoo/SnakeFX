package de.ostfalia.teamx.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Benjamin Wulfert
 *
 * The BaseController is the abstract base class for every ui controller within the front-end application.
 */
public abstract class BaseController {

    public Stage currentStage;

    public void initialize(){

    }

    public void setTitle(String title){
        currentStage.setTitle(title);
    }

    public void showLayout(String layoutName, String newTitle)  {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(layoutName));
        Parent root = null;
        try {
            root = fxmlLoader.load();

            BaseController baseController = fxmlLoader.getController();

            Stage window = new Stage();
            Scene scene = new Scene(root);

            baseController.currentStage = window;
            baseController.setTitle(newTitle);

            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeStage(){
        currentStage.close();
    }


}
