package de.ostfalia.teamx.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.model.SpielDefinition;
import de.ostfalia.teamx.model.Spieler;
import de.ostfalia.teamx.task.GetGamesTask;
import de.ostfalia.teamx.task.GetPlayerTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomescreenController {

    @FXML
    ListView activePlayers;

    @FXML
    ListView activeGames;

    @FXML
    Button newGame;

    // TODO: implement debug-Mode
    // TODO: implement controller inheritence - with shared members (like debug mode)

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     * This initializes the currently active players and the active games.
     */
    public void initialize(){

        try {
            List<Spieler> spielerList = new GetPlayerTask().getPlayer();
            activePlayers.setItems(FXCollections.observableArrayList(spielerList));

            List<SpielDefinition> gameList = new GetGamesTask().getSpiele();
            activeGames.setItems(FXCollections.observableArrayList(gameList));
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        newGame.setOnAction(onClick -> {
            showNewGameDialog();
        });
    }

    /**
     * Shows a new Dialog in order to setup a new game within the lobby.
     */
    public void showNewGameDialog() {
        try {
            // TODO: the logic for instantiating new windows / stages has been copied and pasted through the application - this should get refactored soon!
            Stage window = new Stage();
            window.setTitle(ApplicationConstants.TITLE_NEW_GAME);
            Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("new_game_view.fxml")));
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
