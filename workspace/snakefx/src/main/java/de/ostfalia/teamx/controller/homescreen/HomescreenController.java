package de.ostfalia.teamx.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.controller.Scenes;
import de.ostfalia.teamx.shared.SpielDefinition;
import de.ostfalia.teamx.shared.Spieler;
import de.ostfalia.teamx.task.GetGamesTask;
import de.ostfalia.teamx.task.GetPlayerTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * The HomescreenController is the first UI after a user got logged into the system.
 * The HomescreenController is responsible for the visualization of current players, active games.
 * Its also responsible for allowing access to the game history, creating a new game and joining a game.
 */
public class HomescreenController extends BaseController {

    @FXML
    ListView activePlayers;

    @FXML
    ListView activeGames;

    @FXML
    Button newGame, disconnect, joinGame, gameHistory;

    // TODO: implement debug-Mode
    // TODO: implement controller inheritence - with shared members (like debug mode)

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader.
     * This initializes the currently active players and the active games.
     */
    public void initialize(){

        super.initialize();

        try {
            List<Spieler> spielerList = new GetPlayerTask().getPlayer();
            activePlayers.setItems(FXCollections.observableArrayList(spielerList));

            List<SpielDefinition> gameList = new GetGamesTask().getSpiele();
            activeGames.setItems(FXCollections.observableArrayList(gameList));
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        newGame.setOnAction(onClick -> { showLayout(Scenes.VIEW_NEW_GAME, ApplicationConstants.TITLE_NEW_GAME); });
        gameHistory.setOnAction(onClick -> { showLayout(Scenes.VIEW_HISTORY, ApplicationConstants.TITLE_HISTORY); });
        disconnect.setOnAction(onClick -> { closeStage(); });
    }

}
