package de.ostfalia.snakecore.controller.homescreen;

import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spielregel;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import javafx.application.Platform;
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
    public Button newGame;

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

        newGame.setOnAction(onclick -> { executeCreateNewGame(); });
        abort.setOnAction(onclick -> { showHomeScreen();});
    }

    /**
     * Create a new game based on the inputs from the user interface
     */
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

        // we've created a spielDefinition within the ui - now transmit it to the backend
        Platform.runLater(() -> {
            application.getStompClient().sendLobbyMessage(new LobbyMessage(application.getSpieler(), spielDefinition));
        });

        // switch to the homescreen again
        showHomeScreen();
    }

}
