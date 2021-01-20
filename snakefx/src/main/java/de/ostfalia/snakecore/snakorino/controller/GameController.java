package de.ostfalia.snakecore.snakorino.controller;

import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.SpielstandErgebnis;
import de.ostfalia.snakecore.model.game.Config;
import de.ostfalia.snakecore.model.game.MapEntity;
import de.ostfalia.snakecore.model.game.Snake;
import de.ostfalia.snakecore.model.game.SnakeColor;
import de.ostfalia.snakecore.model.math.Vector2;
import de.ostfalia.snakecore.model.rendering.CompositeShape;
import de.ostfalia.snakecore.util.GameResources;
import de.ostfalia.snakecore.ws.client.StompMessageListener;
import de.ostfalia.snakecore.ws.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Benjamin Wulfert
 * @author Leonard Reidel
 * <p>
 * The GameCanvasController manages the state of the game canvas - it also contains the game-logic for realizing
 * the snake-game.
 * <p>
 * TODO: document every method and every member
 * TODO: reduce for-loops
 * TODO: make config setable externally
 * TODO: implement multiple scores (their needs to be one for every player)
 */
public class GameController extends BaseController implements EventHandler<KeyEvent>, StompMessageListener {

    // View related
    @FXML
    private Canvas gameCanvas;

    @FXML
    private ListView<Spieler> playerList;

    @FXML
    private Label gameDetails;

    @FXML
    private Button backToHomeScreen;
    // View related

    // CHECK Config.java
    private Config config = new Config();

    // the timeline which is used to realize some kind of update-loop
    private Timeline timeline;

    // the amount of milliseconds each game-tick needs in order to update the games state
    // public double TICK_TIME_AMOUNT = 130;
    public double TICK_TIME_AMOUNT = 125;

    // The current instances of food objects
    private Set<MapEntity> mapEntityList = new HashSet<>();

    // PLAYER STUFF - REFACTOR TO THE SNAKE CLASS
    private int score = 0;
    // PLAYER STUFF

    // a map of players and there corresponding snakes - snake => player
    // we also need a bidirectional mapping to get a player for a snake - player => snake
    private Map<Spieler, Snake> playerSnakeMap = new HashMap<>();
    // we're only updating references on this and never remove elements of the map
    private Map<Snake, Spieler> snakePlayerMap = new HashMap<>();

    // a mapping of snakes and their composite shapes - a snake is represtend via a compositeshape,
    // which is a set of shape instances
    private Map<Snake, CompositeShape> snakeShapeMap = new HashMap<>();

    // the graphicsContext which is used to draw to the screen manually
    private GraphicsContext gc;

    // the current instance of the game
    private RunningGame runningGame;

    // fonts used for drawing ingame texts
    private Font playerNameFont = Font.font("Arial", 10);
    private Font gameOverFont = new Font("Digital-7", 70);

    // a map which contains keycode to direction-vector relations
    private Map<KeyCode, Vector2> inputDirectionMap = new HashMap<>();


    // a map which contains a player to boolean relation which indicates
    // that a player is gameOver
    private Map<Spieler, Boolean> spielerGameOverMap = new HashMap<>();


    @Override
    public void postInitialize() {
        super.postInitialize();
        // before the introduction of the multiplayer mechanism the init. happened here
        // now it takes place in GameController.launchGame()

        // initialize a map which maps a keycode to an direction-vector
        inputDirectionMap.put(KeyCode.SPACE, Vector2.ZERO);
        inputDirectionMap.put(KeyCode.UP, Vector2.UP);
        inputDirectionMap.put(KeyCode.DOWN, Vector2.DOWN);
        inputDirectionMap.put(KeyCode.RIGHT, Vector2.RIGHT);
        inputDirectionMap.put(KeyCode.LEFT, Vector2.LEFT);

        inputDirectionMap.put(KeyCode.R, Vector2.ZERO);
        inputDirectionMap.put(KeyCode.W, Vector2.UP);
        inputDirectionMap.put(KeyCode.S, Vector2.DOWN);
        inputDirectionMap.put(KeyCode.D, Vector2.RIGHT);
        inputDirectionMap.put(KeyCode.A, Vector2.LEFT);
    }

    /**
     * Setup the game based on the GameSessionMessage instance from the
     * This is the first message called when the game gets started.
     */
    public void launchGame(GameSessionMessage gameSessionMessage) {
        this.runningGame = gameSessionMessage.getRunningGame();

        // setup n players
        int numPlayers = runningGame.getActiveClients().size();

        // iterate over the n players - create an instance of a snake and add it to the player <-> snake map
        Color[] playerColors = {Color.PURPLE, Color.BLUE, Color.RED, Color.GREEN};
        for (int i = 0; i < numPlayers; i++) {

            // get a reference to a player
            Spieler spieler = runningGame.getActiveClients().get(i);

            // create a corresponding snake
            Snake playerSnake = new Snake(new Vector2(3, 3 * i), new SnakeColor(playerColors[i]));
            playerSnake.owner = spieler.getName();

            // put it into the hashmap for later use (player related, via communication)
            playerSnakeMap.put(spieler, playerSnake);
            snakePlayerMap.put(playerSnake, spieler);

            // snakeShapeMap.put(playerSnake, new CompositeShape());
            spielerGameOverMap.put(spieler, false);
        }

        // setup the player view
        playerList.getItems().addAll(runningGame.getActiveClients());

        // if a player has lost - allow him to get back to the homescreen
        backToHomeScreen.setOnAction(onClick -> {
            showHomeScreen();
        });

        // setup the game details
        gameDetails.setText(
                "Admin: " + runningGame.admin + "\n" +
                        "Anzahl der Spieler: " + runningGame.activeClients.size() + "\n" +
                        "Anzahl der max. Powerups: " + runningGame.getSpielDefinition().getMaxNumberOfPowerUps() + "\n" +
                        "Map-Größe: " + runningGame.getSpielDefinition().getMapWidth() + "x" + runningGame.getSpielDefinition().getMapHeight()
        );

        // setup the dimension of the canvas, but it shouldn't get resized actually....................
        /*
        gameCanvas.setHeight(config.height);
        gameCanvas.setWidth(config.width);
        */

        Pane parentPane = (Pane) gameCanvas.getParent();
        gameCanvas.widthProperty().bind(parentPane.widthProperty());
        gameCanvas.heightProperty().bind(parentPane.heightProperty());

        gameCanvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            config.width = newValue.intValue();
        });
        gameCanvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            config.height = newValue.intValue();
        });

        config.height = (int) gameCanvas.getHeight();
        config.width = (int) gameCanvas.getWidth();
        config.columns = runningGame.spielDefinition.getMapWidth();
        config.rows = runningGame.spielDefinition.getMapHeight();

        // update the title
        setTitle(ApplicationConstants.TITLE_CURRENT_GAME);

        // generate the initial food position
        if (gameSessionMessage.getFoods() != null) {
            spawnFood(gameSessionMessage.getFoods());
        }

        // register the stompClient to this instance of a gameController
        // this will result in:
        // - sending messages to the server when a player omits input
        // - receiving messages from the server when something game related happens
        getApplication().getStompClient().setStompMessageListener(this);


        // display the rendering canvas
        gc = gameCanvas.getGraphicsContext2D();

        // setup the input listener
        // currentStage.getScene().setOnKeyPressed(this);
        currentStage.getScene().setOnKeyReleased(this);

        // The game animation happens because of the timeline
        // every change happens in a new keyFrame (update-loop)
        // and the keyframe is generated by run
        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_TIME_AMOUNT), event -> update(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    /**
     * The update-loop of the game.
     * Gets called every TICK_TIME_AMOUNT until someone wins or some other kind of rule is fulfilled.
     *
     * @param gc - The graphics context in order to draw something on the canvas
     */
    private synchronized void update(GraphicsContext gc) {

        // if every player is in game over state we dont need to update anything anymore
        if (spielerGameOverMap.values().stream().allMatch(v -> v)) {
            timeline.stop();
            return;
        }

        // draw the checkerboard pattern, update the unused contents of the canvas
        drawBackground(gc);

        updatePositions();

        // visualize the game with its players (the snakes), the food, the score, etc.
        drawFood(gc);
        drawSnake(gc);
        drawScore(gc);

        // check if something happened
        checkEatFood();
        checkGameOver();

        // if the user of the front-end is indicated as game over
        // draw the game over message as THE LAST STEP in the rendering pipeline
        if (isLocalPlayerGameOver()) {

            // if the player has lost, allow him to interact with the sidebar
            backToHomeScreen.setDisable(false);
            playerList.setDisable(false);
            playerList.getParent().setDisable(false);

            drawGameOver(gc);

        }

        // check game state
        // if there is only one not-gameover player left, send a message that the game has been played
        // there exists only one winner, so for a 1 on 1 or two player game there can only be one winner and one loser
        // for three players there are 2 loosers and one winner, and so on.
        int totalLosers = (int) spielerGameOverMap.keySet().stream().filter(spieler -> {
            // if the current element within the stream equals true it means the player lost the game
            return spielerGameOverMap.get(spieler);
        }).count();

        if (totalLosers == playerSnakeMap.size()) {
            // prepare game message to inform the backend that a game has been played and a winner and some loosers have been found.
            GameSessionMessage winnerLoserMessage = new GameSessionMessage();

            List<Spieler> loser = spielerGameOverMap.keySet().stream().filter(spieler -> {
                return spielerGameOverMap.get(spieler);
            }).collect(Collectors.toList());


            List<SpielstandErgebnis> loserResults = new LinkedList<>();
            loser.forEach(noob -> {
                SpielstandErgebnis noobResult = new SpielstandErgebnis(score, noob);
                loserResults.add(noobResult);
            });

            winnerLoserMessage.loserResults = loserResults;

            winnerLoserMessage.setGameState(GameSessionMessage.GameState.FINISHING);

            getApplication().getStompClient().sendGameInputMessage(
                    runningGame.stompPath,
                    winnerLoserMessage
            );
        }

        // if there is one player who is not a loser, it must mean he is the winner
        if (totalLosers - 1 == playerSnakeMap.keySet().size()) {

            // prepare game message to inform the backend that a game has been played and a winner and some loosers have been found.
            GameSessionMessage winnerLoserMessage = new GameSessionMessage();

            Spieler winner = spielerGameOverMap.keySet().stream().filter(spieler -> {
                return !spielerGameOverMap.get(spieler);
            }).findFirst().get();
            List<Spieler> loser = spielerGameOverMap.keySet().stream().filter(spieler -> {
                return spielerGameOverMap.get(spieler);
            }).collect(Collectors.toList());


            List<SpielstandErgebnis> loserResults = new LinkedList<>();
            loser.forEach(noob -> {
                SpielstandErgebnis noobResult = new SpielstandErgebnis(score, noob);
                loserResults.add(noobResult);
            });

            winnerLoserMessage.loserResults = loserResults;
            winnerLoserMessage.winnerResult = new SpielstandErgebnis(score, winner);

            winnerLoserMessage.setGameState(GameSessionMessage.GameState.FINISHING);

            getApplication().getStompClient().sendGameInputMessage(
                    runningGame.stompPath,
                    winnerLoserMessage
            );
        }

    }

    private void updatePositions() {

        // update the body-element positions for each snake
        for (Snake snake : playerSnakeMap.values()) {

            if (snake.currentDirection == Vector2.ZERO) {
                // if the direction of a player is zero (= not moving) dont do anything
            } else {
                // calculate the current position for each snake
                for (int i = snake.body.size() - 1; i >= 1; i--) {
                    snake.body.get(i).x = snake.body.get(i - 1).x;
                    snake.body.get(i).y = snake.body.get(i - 1).y;
                }

                // add the current direction vector to the head of the snake,
                // make every element after the head follow its movements
                snake.head = snake.head.add(snake.currentDirection);
            }

        }

        // check for player & wall collisions
        for (Snake snake : playerSnakeMap.values()) {

            // hitting a wall teleports a player to the other side
            if (snake.head.x < 0) {
                snake.head.x = config.rows - 1;
            }
            if (snake.head.y < 0) {
                snake.head.y = config.columns - 1;
            }
            if (snake.head.x > config.rows - 1) {
                snake.head.x = 0;
            }
            if (snake.head.y > config.columns - 1) {
                snake.head.y = 0;
            }

            // if a snake hits itself, the related player of the snake has lost the game
            for (int i = 1; i < snake.body.size(); i++) {
                if (snake.head.x == snake.body.get(i).getX() && snake.head.getY() == snake.body.get(i).getY()) {

                    // update the game-over state for this player
                    Spieler currentPlayerForSnake = snakePlayerMap.get(snake);
                    spielerGameOverMap.put(currentPlayerForSnake, true);

                    break;
                }
            }

        }

    }


    /**
     * This method gets called when a stomp-message has been received from the server
     *
     * @param msg - The message received from the server
     */
    @Override
    public void onGameSessionMessageReceived(GameSessionMessage msg) {

        // handle events while the game is actively running
        if (msg.getGameState() == GameSessionMessage.GameState.RUNNING) {

            // spawn a food instance with a valid position calculated by the server
            if (msg.getFoods() != null) {
                spawnFood(msg.getFoods());
            }

            // if the player is not null
            if (msg.getSender() != null && msg.getInput() != null) {

                // sync. the movement of the player
                if (msg.getInput() != null) {

                    // get the next direction by the input of the player
                    Vector2 newDirection = inputDirectionMap.get(msg.getInput());

                    // update the players snake from the remote site
                    Snake snake = playerSnakeMap.get(msg.getSender());
                    for (int i = 0; i < msg.spielerSnake.body.size(); i++) {
                        snake.body.get(i).set(msg.spielerSnake.body.get(i));
                    }
                    snake.head.set(msg.spielerSnake.head);

                    // change the direction of the corresponding players snake
                    snake.currentDirection = newDirection;
                }

            }

        }

    }


    /**
     * Handles the input of the players.
     * Every time a player enters an input, a message containing this input is sent to the server
     *
     * @param event - The input event
     */
    @Override
    public synchronized void handle(KeyEvent event) {

        // get the current input keycode of the last input
        KeyCode playerInput = event.getCode();


        // if the local player has lost the game we dont accept any key inputs anymore
        if (isLocalPlayerGameOver()) {
            return;
        }

        Snake currentSnake = playerSnakeMap.get(getApplication().getSpieler());
        Vector2 dir = currentSnake.currentDirection;

        // prevent a player from moving into its own snake
        // if a player moves right - changing his head direction to left would result in game over
        // if a player moves up    - changing his head direction to down would result in game over
        // ..
        if (dir == Vector2.UP && playerInput == KeyCode.DOWN) return;
        if (dir == Vector2.DOWN && playerInput == KeyCode.UP) return;
        if (dir == Vector2.LEFT && playerInput == KeyCode.RIGHT) return;
        if (dir == Vector2.RIGHT && playerInput == KeyCode.LEFT) return;

        // send the current input of the client to the backend
        GameSessionMessage gameInputMessage = new GameSessionMessage(
                GameSessionMessage.GameState.RUNNING,
                getApplication().getSpieler(),
                runningGame.getSpielDefinition().getNameOfTheGame(),
                event.getCode()
        );

        // transfer the players snake
        gameInputMessage.spielerSnake = playerSnakeMap.get(getApplication().getSpieler());

        getApplication().getStompClient().sendGameInputMessage(
                runningGame.stompPath,
                gameInputMessage
        );

        // if a player presses escape on his keyboard make him leave the game
        if (playerInput == KeyCode.ESCAPE) {
            currentStage.close();
            // TODO: send message to notify that a player has left a game
        }

    }


    /**
     * Check if a snake has eat some food.
     */
    private void checkEatFood() {

        // we'll have to collect the food which got to be removed after looping or else well get a concurrentModificationException
        boolean isFrameRemoval = false;
        MapEntity toRemove = null;

        // iterate over every snake within the game
        for (Snake snake : playerSnakeMap.values()) {

            // the coordinates of the snakes head
            int sxcord = snake.head.getX();
            int sycord = snake.head.getY();

            // iterate over every food instance
            for (MapEntity mapEntity : mapEntityList) {

                // if the head matches the position of a food
                if (sxcord == mapEntity.getPosition().x && sycord == mapEntity.getPosition().y) {

                    // mark the corresponding food to be removed from the game board
                    isFrameRemoval = true;
                    toRemove = mapEntity;

                    // make the corresponding snake of the player one element longer
                    snake.addBodyElement();

                    /*
                    // deciding effect of food based on randomness
                    Random ran = new Random();
                    int x = 1 + ran.nextInt(10);

                    if (x < 8) {
                        // add an un-initialized body-part to the snake
                        snake.isPredator = false;
                    } else {
                        snake.isPredator = true;
                        //EVTL FARBE VON SCHLANGE ÄNDERN UND SOUNDEFFEKT EINBAUEN. Geschmackssache.
                        //Oder man erkennt nur anhand der nicht geänderten länge, wenn man predator ist, dass es schwieriger wird
                    }
                    */

                    // play pickup sound when player collects some food.
                    getApplication().getSoundManager().playPickup2();

                }
            }

        }

        /*

        // manage removal of food
        // if a food has been marked for removal
        if (isFrameRemoval) {

            // remove the current food - by adding it to the removal collection
            foodList.remove(toRemove);

            // send a gameSessionMessage which indicates that a food has been removed
            GameSessionMessage gameInputMessage = new GameSessionMessage(
                    GameSessionMessage.GameState.RUNNING,
                    runningGame.getSpielDefinition().getNameOfTheGame(),
                    snakeList,
                    config,
                    true
            );
            gameInputMessage.setRunningGame(runningGame);
            gameInputMessage.setInput(null);

            // before sending - we nullify the drawables of the food
            // else sending a message will result in a marshalling exception
            for (Food food : foodList) {
                food.drawable = null;
            }

            // send all food positions to the backend
            gameInputMessage.setFoods(foodList);
            gameInputMessage.amountOfFoodDrawables = GameResources.FOOD_IMAGE_PATHS.length;


            // send the message
            getApplication().getStompClient().sendGameInputMessage(
                    runningGame.stompPath,
                    gameInputMessage
            );

            // TODO: correctly calculate the players score
            // increase the players score
            score += 5;

        }

        */

        //check that predator snake doesn't eat itself
        /*
        for (Snake snake : snakeList) {
            for (Snake otherSnake : snakeList) {

                if (otherSnake != snake) {

                    for (Vector2 part : otherSnake.body) {
                        if (snake.head.equals(part)) {
                            if (!snake.isPredator) {
                                checkGameOver();
                            } else {
                                int totalLenght = otherSnake.body.size();
                                int splittingPoint = otherSnake.body.indexOf(part);
                                int growth = totalLenght - splittingPoint;

                                for (int i = splittingPoint; i < totalLenght; i++) {
                                    //cut bitten Snakes body
                                    otherSnake.body.remove(i);

                                    //add to biting Snake
                                    Vector2 newPart = new Vector2(-1, -1);
                                    snake.body.add(newPart);
                                }
                            }
                        }
                    }
                }
            }
        }
        */

    }


    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(gameOverFont);
        gc.fillText("Game Over", config.width / 3.5, config.width / 2);
    }

    /**
     * Draw a players name to the given position
     */
    private void drawPlayerName(GraphicsContext gc, String playerName, Vector2 pos) {
        gc.setFont(playerNameFont);
        gc.fillText(playerName, pos.x, pos.y);
    }

    /**
     * Draw the checker-board pattern to / as the background of the game.
     */
    private void drawBackground(GraphicsContext gc) {

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // for each row ...
        for (int i = 0; i < config.rows; i++) {

            // for each column
            for (int j = 0; j < config.columns; j++) {

                // if i+j is even - draw a slightly brighter grey tone
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("161718"));
                }
                // if i+j is odd - draw a slightly darker grey tone
                else {
                    gc.setFill(Color.web("34383B"));
                }

                // draw the rect
                gc.fillRect(i * config.tileSize, j * config.tileSize,
                        config.tileSize, config.tileSize);
            }

        }

    }

    /**
     * Generate food for the players within the map.
     * - Dont generate a food below a players head
     * - Dont generate a food below a players body
     */
    private void spawnFood(Set<MapEntity> foodsToSpawn) {

        // remove any food on the gamefield
        mapEntityList.clear();

        // System.out.println("Generating food: " + foodsToSpawn.size());
        for (MapEntity mapEntity : foodsToSpawn) {
            mapEntity.drawable = new Image(GameResources.FOOD_IMAGE_PATHS[mapEntity.drawableId]); // TODO - Update food drawable id generation
            mapEntityList.add(mapEntity);
        }

    }

    /**
     * Draw the food, which has been generated bevor
     *
     * @param gc
     */
    private void drawFood(GraphicsContext gc) {
        for (MapEntity mapEntity : mapEntityList) {
            gc.drawImage(
                    mapEntity.drawable,
                    mapEntity.getPosition().x * config.tileSize,
                    mapEntity.getPosition().y * config.tileSize,
                    config.tileSize,
                    config.tileSize
            );
        }
    }

    /**
     * Draw the snake and their body-parts based on their position
     *
     * @param gc
     */
    private void drawSnake(GraphicsContext gc) {

        for (Snake snake : playerSnakeMap.values()) {

            // draw the head of the snake
            gc.setFill(snake.color.toJavaFxColor().brighter());
            gc.fillRoundRect(
                    snake.head.getX() * config.tileSize,
                    snake.head.getY() * config.tileSize,
                    config.tileSize - 1,
                    config.tileSize - 1,
                    35,
                    35
            );

            // draw the body of the snake
            gc.setFill(snake.color.toJavaFxColor());
            for (int i = 1; i < snake.body.size(); i++) {
                gc.fillRoundRect(
                        snake.body.get(i).getX() * config.tileSize,
                        snake.body.get(i).getY() * config.tileSize,
                        config.tileSize - 1,
                        config.tileSize - 1,
                        20,
                        20
                );
            }

            // draw the name of the player centered on their heads
            gc.setFill(Color.WHITE);
            for (Map.Entry<Spieler, Snake> spielerSnakeEntry : playerSnakeMap.entrySet()) {
                if (spielerSnakeEntry.getValue().equals(snake)) {
                    Vector2 pos = new Vector2(snake.head.x * config.tileSize + 2, snake.head.y * config.tileSize + (config.tileSize / 2));
                    drawPlayerName(gc, spielerSnakeEntry.getKey().getName(), pos);
                }
            }
        }
    }

    /**
     * Draw the score of a player
     *
     * @param gc
     */
    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    /**
     * Check if the game is finished.
     */
    public void checkGameOver() {

        // check for snake-collision
        for (Snake a : playerSnakeMap.values()) {
            for (Snake b : playerSnakeMap.values()) {

                if (a.head != b.head && a.head.x == b.head.x && a.head.y == b.head.y) {

                    // Get the player who has lost
                    Spieler lostTheGamePlayer = snakePlayerMap.get(a);
                    spielerGameOverMap.put(lostTheGamePlayer, true);
                    playerSnakeMap.get(lostTheGamePlayer).currentDirection = Vector2.ZERO;

                }

                for (Vector2 abody : a.body) {
                    for (Vector2 bbody : b.body) {
                        // check if abody is not actually abody again
                        // check if x of snake a is x of snake b
                        // check if y of snake a is y of snake b
                        if (abody != bbody && abody.x == bbody.x && abody.y == bbody.y
                                && abody.x != -1 && abody.y != -1 && bbody.x != -1 && bbody.y != -1) {

                            // Get the player who has lost
                            Spieler lostTheGamePlayer = snakePlayerMap.get(a);
                            spielerGameOverMap.put(lostTheGamePlayer, true);
                            playerSnakeMap.get(lostTheGamePlayer).currentDirection = Vector2.ZERO;

                        }
                    }
                }
            }
        }
    }

    @Override
    public void onChatMessageReceived(ChatMessage msg) {
    }

    @Override
    public void onLobbyMessageReceived(LobbyMessage msg) {
    }

    @Override
    public void onPlayerMessageReceived(PlayerMessage msg) {
    }

    @Override
    public void onPlayerJoinedGameMessageReceived(PlayerJoinsGameMessage msg) {
    }

    // getter & setter & utils

    /**
     * Get a player instance from within this game canvas based on the currently logged in user
     *
     * @return - the player for the currently logged in user
     */
    public Spieler getLocalPlayer() {
        Spieler result = null;
        for (Spieler spieler : playerSnakeMap.keySet()) {
            if (spieler.name.equalsIgnoreCase(getApplication().getSpieler().getName())) {
                result = spieler;
            }
        }
        return result;
    }


    /**
     * Indicates if the current / local player (the user who useses the front end currently) has lost the game.
     *
     * @return - true if the local user has lost the game
     */
    public boolean isLocalPlayerGameOver() {
        boolean result = false;

        Spieler current = getLocalPlayer();
        if (current != null && spielerGameOverMap.containsKey(current)) {
            if (spielerGameOverMap.get(current)) {
                result = true;
            }
        }

        return result;
    }

}
