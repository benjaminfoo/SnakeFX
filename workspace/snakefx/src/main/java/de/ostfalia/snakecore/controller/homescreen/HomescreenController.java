package de.ostfalia.snakecore.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.task.GetGamesTask;
import de.ostfalia.snakecore.task.GetPlayerTask;
import de.ostfalia.snakecore.ws.client.StompSessionHandler;
import de.ostfalia.snakecore.ws.model.Message;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

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

    @FXML
    TextArea chatContent;

    @FXML
    TextField userContent;

    @FXML
    Button sendUserContent;

    // TODO: implement debug-Mode
    // TODO: implement controller inheritence - with shared members (like debug mode)

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader.
     * This initializes the currently active players and the active games.
     */
    public void initialize() {

        super.initialize();

        newGame.setOnAction(onClick -> {
            showLayout(Scenes.VIEW_NEW_GAME, ApplicationConstants.TITLE_NEW_GAME);
        });

        gameHistory.setOnAction(onClick -> {
            showLayout(Scenes.VIEW_HISTORY, ApplicationConstants.TITLE_HISTORY);
        });

        disconnect.setOnAction(onClick -> {
            closeStage();
        });

    }

    private void updateUIRemote() {
        try {
            activePlayers.getItems().clear();
            activeGames.getItems().clear();

            List<Spieler> spielerList = new GetPlayerTask().getPlayer();
            activePlayers.setItems(FXCollections.observableArrayList(spielerList));

            List<SpielDefinition> gameList = new GetGamesTask().getSpiele();
            activeGames.setItems(FXCollections.observableArrayList(gameList));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postInitialize() {
        super.postInitialize();

        updateUIRemote();

        application.getStompClient().connect();
        application.getStompClient().setRecievedCallback(new StompSessionHandler.MessageRecievedCallback() {
            @Override
            public void messageRecieved(Message msg) {
                chatContent.appendText(msg.getFrom() + ": " + msg.getText() + "\n");
            }
        });

        sendUserContent.setOnAction(onClick -> {
            application.getStompClient().sendMessage(
                    application.getUserConfig().getUserName(),
                    userContent.getText()
            );
            userContent.clear();
        });


        // reuse the onKeyReleased listener from the name
        userContent.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                sendUserContent.fire();
            }
        });
    }
}
