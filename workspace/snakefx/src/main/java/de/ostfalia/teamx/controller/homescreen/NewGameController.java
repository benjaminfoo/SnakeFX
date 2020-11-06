package de.ostfalia.teamx.controller.homescreen;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.shared.SpielDefinition;
import de.ostfalia.teamx.shared.Spielregel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author Benjamin Wulfert
 *
 * The NewGameController is responsible for creating new games (Spielrunden / Spieldefinitionen).
 * The NewGameController can be accessed from the HomescreenController.
 */
public class NewGameController extends BaseController {

    @FXML
    Button newGame;

    @FXML
    Button abort;

    @FXML
    Spinner<Integer> numberOfPlayers, numberOfPowerups;

    @FXML
    TextField mapWidth, mapHeight, nameOfTheGame;

    @FXML
    ComboBox<Spielregel> ruleSet;

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize(){
        super.initialize();

        numberOfPlayers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2,10,1));
        numberOfPowerups.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,1));
        ruleSet.setValue(new Spielregel("Highscore: 100Pts", false));

        newGame.setOnAction(onclick -> { executeCreateNewGame(); });
        abort.setOnAction(onclick -> { closeStage();});
    }

    private void executeCreateNewGame() {

        String newName = nameOfTheGame.getText().trim();
        int numberOfPlayer = numberOfPlayers.getValue();
        int numberOfPowerUps = numberOfPowerups.getValue();
        Spielregel spielregel = ruleSet.getValue();

        // TODO: check if the values of the map are actual numerical values, not text or alphanumerical input !
        // TODO: maybe put a regex on the textfields or use some other control
        int mapSizeX = Integer.parseInt(mapWidth.getText());
        int mapSizeY = Integer.parseInt(mapHeight.getText());

        SpielDefinition spielDefinition = new SpielDefinition();
        spielDefinition.setNameOfTheGame(newName);
        spielDefinition.setMapHeight(mapSizeY);
        spielDefinition.setMapWidth(mapSizeX);
        spielDefinition.setNumberOfPlayer(numberOfPlayer);
        spielDefinition.setMaxNumberOfPowerUps(numberOfPowerUps);
        spielDefinition.setSpielregel(spielregel);

        Gson gson = new Gson();

        try {
            System.out.println("Registering new game: " + gson.toJson(spielDefinition));
            HttpResponse<String> res = Unirest
                    .post(ProjectEndpoints.URL_API_LOBBY)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(spielDefinition))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

}
