package de.ostfalia.snakecore;

import de.ostfalia.snakecore.app.BaseApplication;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.controller.login.LoginController;
import de.ostfalia.snakecore.util.BannerPrinter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * @author Benjamin Wulfert
 *
 * The entry point of the javafx-application.
 * Diese Klasse ausführen um den JavaFX-Client zu starten.
 */
public class AppSnakeFX extends BaseApplication {

    // plain old psvm - the entry point of the application
    public static void main(String[] args) {
        launch(args);
    }

    public AppSnakeFX() {
    }

    public AppSnakeFX(String userName, String password) {

        // pass the reference to the args variables from the main method
        setStartParams(Arrays.asList(userName, password));
    }

    @Override
    public void start(Stage window) throws Exception {

        // safe the reference to the current window - we'll safe it for future uses
        setWindowStage(window);

        // this loads the login-controller and the corresponding fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource(Scenes.VIEW_LOGIN));
        Parent root = fxmlLoader.load();

        BaseController baseController = fxmlLoader.getController();
        baseController.currentStage = window;
        baseController.setApplication(this);
        baseController.currentStage.getIcons().add(new Image("icon.png"));
        baseController.setTitle(ApplicationConstants.TITLE_LOGIN);

        this.initializedController.put(LoginController.class, baseController);

        // setup the stage of the application
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


        // if the user presses F12 on his keyboard, the debug scene-viewer gets called and displayed
        window.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F12){
                baseController.showLayoutInNewWindow(Scenes.VIEW_DEBUG, ApplicationConstants.TITLE_DEBUG_CHOOSER);
            }
        });

        new BannerPrinter().printBanner();

        ((LoginController) baseController).postInitialize();

        String musicFile = "StayTheNight.mp3";     // For example

    }


}
