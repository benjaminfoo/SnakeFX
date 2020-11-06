package de.ostfalia.teamx.controller.homescreen;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.model.SpielDefinition;
import de.ostfalia.teamx.model.Spieler;
import de.ostfalia.teamx.model.Spielregel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewGameController {

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
     * Initialize the user-interface.
     * Setup the creation of a new game with the provided values from the UI.
     */
    public void initialize(){

        numberOfPlayers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2,10,1));
        numberOfPowerups.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,1));
        ruleSet.setValue(new Spielregel("Highscore: 100Pts", false));

        newGame.setOnAction(onclick -> {
            executeCreateNewGame();
        });

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
                    .post(ProjectEndpoints.HOST_URL_API_LOBBY)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(spielDefinition))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

}
