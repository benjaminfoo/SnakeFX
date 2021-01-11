package de.ostfalia.snakecore.controller;

import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.app.BaseApplication;
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
    public BaseApplication application;

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

    public void setTitle(String title) {
        if (title.equals(ApplicationConstants.TITLE_LOGIN)) {
            currentStage.setTitle(title);
        } else {
            currentStage.setTitle(title + " - Connected to: " + ProjectEndpoints.URL_BASE + " | " + ProjectEndpoints.URL_STOMP_BASE);
        }

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
            baseController.application = application;

            // pass the reference of the current window stage to the newly instantiated controller
            baseController.currentStage = currentStage;
            currentStage.setScene(new Scene(root));

            // setup the title and the icon of the application
            baseController.currentStage.getIcons().add(new Image("icon.png"));
            baseController.setTitle(newTitle);

            application.initializedController.put(baseController.getClass(), baseController);
            System.out.println("Putting: " + baseController.getClass() + " with " + baseController.getClass() + " into controller cache");

            // initialize everything related to the user-interface
            baseController.postInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showLayoutInNewWindow(String layoutName, String newTitle)  {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(layoutName));
        Parent root = null;

        try {

            // load the fxml by the given layoutName
            root = fxmlLoader.load();

            // get a reference to its corresponding controller
            BaseController baseController = fxmlLoader.getController();
            baseController.application = application;

            // create a new stage, set the newly loaded layout as the root element of it
            Stage window = new Stage();
            Scene scene = new Scene(root);
            window.setScene(scene);

            // setup the controller - reference it to the stage
            baseController.currentStage = window;

            // setup the title and the icon of the application
            baseController.currentStage.getIcons().add(new Image("icon.png"));
            baseController.setTitle(newTitle);
            application.initializedController.put(baseController.getClass(), baseController);

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

    public void showHomeScreen(){
        showLayout(Scenes.VIEW_HOMESCREEN, ApplicationConstants.TITLE_HOMESCREEN);
    }

    public void showGameScreen(){
        showLayout(Scenes.VIEW_GAME_CANVAS, ApplicationConstants.TITLE_CURRENT_GAME);
    }

}
