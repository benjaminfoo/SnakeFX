package de.ostfalia.teamx.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Benjamin Wulfert
 *
 * The BaseController is the abstract base class for every ui controller within the front-end application.
 */
public abstract class BaseController {

    public Stage currentStage;

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize(){

    }

    /**
     * This optional method gets called AFTER the UI has been initialized.
     */
    public void postInitialize(){
        // accessing the UI within the initialize method would cause a NullPointerException
    }

    public void setTitle(String title){
        currentStage.setTitle(title);
    }

    /**
     * Shows the given layout within a new stage - using the given title.
     * @param layoutName
     * @param newTitle
     */
    public void showLayout(String layoutName, String newTitle)  {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(layoutName));
        Parent root = null;

        try {

            // load the fxml by the given layoutName
            root = fxmlLoader.load();

            // get a reference to its corresponding controller
            BaseController baseController = fxmlLoader.getController();

            // create a new stage, set the newly loaded layout as the root element of it
            Stage window = new Stage();
            Scene scene = new Scene(root);
            window.setScene(scene);

            // setup the controller - reference it to the stage
            baseController.currentStage = window;

            // setup the title and the icon of the application
            baseController.currentStage.getIcons().add(new Image("icon.png"));
            baseController.setTitle(newTitle);

            // initialize everything related to the user-interface
            baseController.postInitialize();

            // show the setup and referenced window
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void closeStage(){
        currentStage.close();
    }


}
