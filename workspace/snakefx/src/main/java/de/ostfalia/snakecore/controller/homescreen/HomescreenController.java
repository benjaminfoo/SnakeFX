package de.ostfalia.snakecore.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.task.GetPlayerTask;
import de.ostfalia.snakecore.ws.client.MessageRecievedCallback;
import de.ostfalia.snakecore.ws.model.ChatMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Abmelden");
            alert.setHeaderText("Möchten Sie sich wirklich abmelden?");
            alert.setContentText("Ihre Sessions sowie alle laufenden Verbindungen werden beendet.");

            alert.showAndWait().ifPresent((btnType) -> {
                if (btnType == ButtonType.OK) {
                    // TODO - disconnect via stomp
                    // TODO - eventually cleanup everything
                    closeStage();
                }
                if (btnType == ButtonType.CANCEL) {
                    alert.close();
                }
            });

        });

        joinGame.setOnAction(onClick -> {
            String roomId = "1337";

            application.getStompClient().sendLobbyMessage();

            // TODO: get all running games via stomp
            // TODO: create a reference per list item <-> roomId

            /*
            application.getStompClient().joinRoom(
                    application.getUserConfig().getUserName(),
                    new JoinGameMessage(application.getUserConfig().getUserName(), roomId)
            );
            */
        });

    }

    private void updateUIRemote() {
        try {
            activePlayers.getItems().clear();
            activeGames.getItems().clear();

            List<Spieler> spielerList = new GetPlayerTask().getPlayer();
            activePlayers.setItems(FXCollections.observableArrayList(spielerList));

            /*
            List<SpielDefinition> gameList = new GetGamesTask().getSpiele();
            activeGames.setItems(FXCollections.observableArrayList(gameList));
             */

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postInitialize() {
        super.postInitialize();

        updateUIRemote();

        application.getStompClient().connect("ws://localhost:8080/snakeserver");
        application.getStompClient().setRecievedCallback(new MessageRecievedCallback() {
            @Override
            public void chatMessageRecieved(ChatMessage msg) {
                chatContent.appendText(msg.getFrom() + ": " + msg.getText() + "\n");
            }

            @Override
            public void onRecievedLobbyMessage(LobbyMessage msg) {
                chatContent.appendText("Currently running games: ");
                for (RunningGame spielDefinition : msg.runningGames) {
                    chatContent.appendText("\t" + spielDefinition.toString());
                }

                activeGames.setItems(FXCollections.observableArrayList(msg.runningGames));


            }

        });


        // subscribe to /topic/games
        // if there is a new message on this topic, parse the results and update the ui


        // TODO - subscribe to /topic/games
        // TODO - send request with GetAllGames message to ... get all the currently running games


        sendUserContent.setOnAction(onClick -> {
            application.getStompClient().sendChatMessage(
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
