package de.ostfalia.teamx.controller.debug;

import de.ostfalia.teamx.AppSnakeFX;
import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.controller.Scenes;
import de.ostfalia.teamx.util.DebugOptions;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * @author Benjamin Wulfert
 * <p>
 * The DebugSceneController is used within the debug_scenechooser.xml - view.
 * The DebugSceneController allows the user to show every stage (window) of the application - its useful for debugging
 * different scenarios without clicking through the happy path first.
 */
public class DebugSceneController extends BaseController {

    @FXML
    Button login;
    @FXML
    Button homescreen;
    @FXML
    Button newgame;
    @FXML
    Button gamehistorie;
    @FXML
    Button gamescene;

    // allows to setup the application in debug-mode
    @FXML
    CheckBox debugMode;

    @FXML
    CheckBox debugLowCore;
    @FXML
    CheckBox debugHardCore;

    @FXML
    CheckBox debugEveryoneNPC;

    @FXML
    CheckBox debugCircling;
    @FXML
    CheckBox debungRandomMovement;

    @FXML
    CheckBox debugDrawDirection;


    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize() {
        super.initialize();

        login.setOnAction(click -> {
            showLayoutInNewWindow(Scenes.VIEW_LOGIN, ApplicationConstants.TITLE_LOGIN);
        });
        homescreen.setOnAction(click -> {
            showLayoutInNewWindow(Scenes.VIEW_HOMESCREEN, ApplicationConstants.TITLE_HOMESCREEN);
        });
        gamehistorie.setOnAction(click -> {
            showLayoutInNewWindow(Scenes.VIEW_HISTORY, ApplicationConstants.TITLE_HISTORY);
        });
        newgame.setOnAction(click -> {
            showLayoutInNewWindow(Scenes.VIEW_NEW_GAME, ApplicationConstants.TITLE_NEW_GAME);
        });
        gamescene.setOnAction(click -> {
            showLayoutInNewWindow(Scenes.VIEW_GAME_CANVAS, ApplicationConstants.TITLE_CURRENT_GAME);
        });

        // Manage the debugmode of the application
        AppSnakeFX.inDebugMode = debugMode.isSelected();
        debugMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            AppSnakeFX.inDebugMode = newValue;
        });

        DebugOptions.DEBUG_EVERYBODY_NPC = debugEveryoneNPC.isSelected();
        debugEveryoneNPC.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_EVERYBODY_NPC = newVal;});

        DebugOptions.DEBUG_HARDCORE = debugHardCore.isSelected();
        debugHardCore.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_HARDCORE = newVal;});

        DebugOptions.DEBUG_LOWCORE = debugLowCore.isSelected();
        debugLowCore.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_LOWCORE = newVal;});

        DebugOptions.DEBUG_NPC_MOVEMENT_CIRCLING = debugCircling.isSelected();
        debugCircling.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_NPC_MOVEMENT_CIRCLING = newVal;});

        DebugOptions.DEBUG_NPC_MOVEMENT_RANDOM_PATHS = debungRandomMovement.isSelected();
        debungRandomMovement.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_NPC_MOVEMENT_RANDOM_PATHS = newVal;});

        DebugOptions.DEBUG_DRAW_DIRECTIONS = debugDrawDirection.isSelected();
        debugDrawDirection.selectedProperty().addListener((o, oldVal, newVal) -> { DebugOptions.DEBUG_DRAW_DIRECTIONS = newVal;});


    }

}
