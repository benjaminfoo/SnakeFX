package de.ostfalia.teamx.controller.debug;

import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.controller.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * @author Benjamin Wulfert
 *
 * The DebugSceneController is used within the debug_scenechooser.xml - view.
 * The DebugSceneController allows the user to show every stage (window) of the application - its useful for debugging
 * different scenarios without clicking through the happy path first.
 */
public class DebugSceneController extends BaseController {

    @FXML Button login;
    @FXML Button homescreen;
    @FXML Button newgame;
    @FXML Button gamehistorie;
    @FXML Button gamescene;
    @FXML CheckBox debugMode;


    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize () {
        super.initialize();

        login.setOnAction(click -> { showLayout(Scenes.VIEW_LOGIN, ApplicationConstants.TITLE_LOGIN); });
        homescreen.setOnAction(click -> { showLayout(Scenes.VIEW_HOMESCREEN, ApplicationConstants.TITLE_HOMESCREEN); });
        gamehistorie.setOnAction(click -> { showLayout(Scenes.VIEW_HISTORY,  ApplicationConstants.TITLE_HISTORY); });
        newgame.setOnAction(click -> { showLayout(Scenes.VIEW_NEW_GAME, ApplicationConstants.TITLE_NEW_GAME); });
        gamescene.setOnAction(click -> { showLayout(Scenes.VIEW_GAME_CANVAS, ApplicationConstants.TITLE_CURRENT_GAME); });

    }

}
