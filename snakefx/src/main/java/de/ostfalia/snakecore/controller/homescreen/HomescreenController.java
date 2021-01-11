package de.ostfalia.snakecore.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.snakorino.controller.GameController;
import de.ostfalia.snakecore.snakorino.controller.GameResources;
import de.ostfalia.snakecore.task.GetGamesTask;
import de.ostfalia.snakecore.task.GetPlayerTask;
import de.ostfalia.snakecore.view.RunningGameCell;
import de.ostfalia.snakecore.ws.client.StompMessageListener;
import de.ostfalia.snakecore.ws.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.util.Date;
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
    public ListView<Spieler> activePlayers;

    @FXML
    public ListView<RunningGame> activeGames;

    @FXML
    public Button newGame, disconnect, joinGame, gameHistory;

    @FXML
    public Button adminStartGame;

    @FXML
    TextArea chatContent;

    @FXML
    TextField userContent;

    @FXML
    Button sendUserContent;

    @FXML
    Label userNameLabel;

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
            RunningGame selectedGame = (RunningGame) activeGames.getSelectionModel().getSelectedItem();
            System.out.println("Subscribing to: " + selectedGame.getStompPath());

            application.getStompClient().sendJoinGameMessage(selectedGame.getStompPath(), application.getSpieler(), selectedGame);
            application.getStompClient().subscribeToGameTopic(selectedGame.getStompPath());
        });

        // manage clicks on the admin-menu -> start game button
        adminStartGame.setOnAction(onClick -> {
            System.out.println("Starting game ...");

            RunningGame selectedGame = (RunningGame) activeGames.getSelectionModel().getSelectedItem();
            System.out.println("Subscribing to: " + selectedGame.getStompPath());

            // subscribe to the newly created game
            application.getStompClient().subscribeToGameTopic(selectedGame.getStompPath());

            // send an initial game session message
            GameSessionMessage gameInputMessage = new GameSessionMessage(GameSessionMessage.GameState.STARTING, application.getUserConfig().getUserName(), selectedGame);
            gameInputMessage.amountOfFoodDrawables = GameResources.FOOD_IMAGE_PATHS.length;

            application.getStompClient().sendGameInputMessage(
                    selectedGame.stompPath,
                    gameInputMessage
            );


        });


        // modifies the state of the ui to certain situations
        activeGames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            RunningGame selectedItem = newValue;

            boolean currentPlayerIsAdminOfSelection = selectedItem.admin.getName().equalsIgnoreCase(application.getSpieler().getName());

            // we have to invert the statement because the button gets disabled if set to true
            adminStartGame.setDisable(!currentPlayerIsAdminOfSelection);

            System.out.println("Selected game: " + newValue.spielDefinition.getNameOfTheGame());

            // disable the join-game button when the player limit is reached - enable it if not
            boolean playerLimitReached = selectedItem.activeClients.size() == selectedItem.getSpielDefinition().getNumberOfPlayer();
            // disable the join game button then the player already joined the game
            boolean playerAlreadyInLobby = selectedItem.activeClients.contains(application.getSpieler());

            joinGame.setDisable(playerLimitReached || playerAlreadyInLobby);

        });

    }



    @Override
    public void postInitialize() {
        super.postInitialize();

        userNameLabel.setText("Hello, " + application.getUserConfig().getUserName() + "!");

        // prepare the ui for displaying listCells
        activeGames.setCellFactory(listView -> new RunningGameCell());

        // update the list of active players
        updatePlayerList();

        // update the list of running games
        updateLobbyList();

        // register callbacks for updating the UI and getting information about currently running games, etc.
        application.getStompClient().setStompMessageListener(new StompMessageListener() {

            @Override
            public void onGameSessionMessageRecieved(GameSessionMessage msg) {
                if(msg.getGameState() == GameSessionMessage.GameState.STARTING) {
                    Platform.runLater(() -> {
                        showGameScreen();
                        ((GameController) application.initializedController.get(GameController.class)).launchGame(msg);
                    });
                }
            }

            @Override
            public void onChatMessageReceived(ChatMessage msg) {
                chatContent.appendText("(" + application.getSimpleDateFormat().format( new Date()) + "): " + msg.getFrom() + ": " + msg.getText() + "\n");
            }

            @Override
            public void onLobbyMessageReceived(LobbyMessage msg) {

                activeGames.getItems().clear();
                for (RunningGame runningGame : msg.runningGames) {
                    activeGames.getItems().add(runningGame);
                }

            }

            @Override
            public void onPlayerMessageReceived(PlayerMessage msg) {

                activePlayers.getItems().clear();
                for (Spieler spieler : msg.playersInLobby) {
                    activePlayers.getItems().add(spieler);
                }

            }

            @Override
            public void onPlayerJoinedGameMessageReceived(PlayerJoinsGameMessage msg) {

                activeGames.getItems().clear();
                for (RunningGame runningGame : msg.allGames) {
                    activeGames.getItems().add(runningGame);
                }

            }

        });


        // if the user presses the "send" button on the lower right, send the chat message to the server
        sendUserContent.setOnAction(onClick -> {
            application.getStompClient().sendChatMessage(
                    application.getUserConfig().getUserName(),
                    userContent.getText()
            );
            userContent.clear();
        });


        // if the user hits the "enter"-key, reuese the functionality from the send button
        userContent.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                sendUserContent.fire();
            }
        });

    }

    // update the player-listView
    private void updatePlayerList() {
        try {

            // clear the list of players
            activePlayers.getItems().clear();

            // retrieve the currently logged in players
            List<Spieler> spielerList = new GetPlayerTask().getPlayer();

            // update the ui to show every player
            activePlayers.getItems().addAll(spielerList);

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    // update the games-listView
    private void updateLobbyList() {
        try {

            // clear the list of players
            activeGames.getItems().clear();

            // retrieve the currently logged in players
            List<RunningGame> gamesList = new GetGamesTask().getSpiele();

            // update the ui to show every player
            activeGames.getItems().addAll(gamesList);

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    // for testing async ui
    public void simulateChatMessage(String input){
        userContent.setText(input);
        sendUserContent.fire();
    }



}
