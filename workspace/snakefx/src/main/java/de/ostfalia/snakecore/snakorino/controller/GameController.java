package de.ostfalia.snakecore.snakorino.controller;

import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.snakorino.model.Config;
import de.ostfalia.snakecore.snakorino.model.Food;
import de.ostfalia.snakecore.snakorino.model.Snake;
import de.ostfalia.snakecore.snakorino.model.Vector2;
import de.ostfalia.snakecore.util.RNG;
import de.ostfalia.snakecore.ws.client.StompMessageListener;
import de.ostfalia.snakecore.ws.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

import static de.ostfalia.snakecore.util.DebugOptions.*;

/**
 * @author Benjamin Wulfert
 * @author Leonard Reidel
 *
 * The GameCanvasController manages the state of the game canvas - it also contains the game-logic for realizing
 * the snake-game.
 *
 * TODO: document every method and every member
 * TODO: reduce for-loops
 * TODO: make config setable externally
 * TODO: implement multiple scores (their needs to be one for every player)
 */
public class GameController extends BaseController implements EventHandler<KeyEvent> {

    // the configuration in order to setup a game

    // CHECK Config.java
    private Config config = new Config();

    private static final String[] FOOD_IMAGE_PATHS = new String[]{
            "food/ic_orange.png",
            "food/ic_apple.png",
            "food/ic_cherry.png",
            "food/ic_berry.png",
            "food/ic_coconut_.png",
            "food/ic_peach.png",
            "food/ic_watermelon.png",
            "food/ic_orange.png",
            "food/ic_pomegranate.png"
    };

    // the timeline which is (ab-)used to realize some kind of update-loop
    private Timeline timeline;

    // the amount of milliseconds each game-tick needs in order to update the games state
    // public double TICK_TIME_AMOUNT = 130;
    public double TICK_TIME_AMOUNT = 130;
    public double TICK_TIME_CHANGE_AMOUNT = 10;
    // In den GameSetController î

    // FOOD STUFF - Extract to class Food.java
    // The image of the current food
    List<Food> foodList = new LinkedList<Food>();
    // FOOD STUFF

    // States
    private boolean gameOver;
    private boolean paused;

    // PLAYER STUFF - REFACTOR TO THE SNAKE CLASS
    private int score = 0;
    // PLAYER STUFF

    // refactor for multiplayer
    private int numPlayers;
    private List<Snake> snakeList = new LinkedList<>();

    KeyCode[] firstPlayerControls = {
            KeyCode.W,
            KeyCode.A,
            KeyCode.S,
            KeyCode.D
    };
    KeyCode[] secondPlayerControls = {
            KeyCode.UP,
            KeyCode.LEFT,
            KeyCode.DOWN,
            KeyCode.RIGHT
    };
    // refactor for multiplayer

    // the graphicsContext which is used to draw to the screen manually
    private GraphicsContext gc;

    public void launchGame(RunningGame runningGame){

        numPlayers = runningGame.getActiveClients().size();

        Color[] playerColors = {Color.PURPLE, Color.BLUE, Color.RED, Color.GREEN};
        for (int i = 0; i < numPlayers; i++) {
            snakeList.add ( new Snake ( new Vector2(3, 3 * i), playerColors[i]));
        }

        for (Snake snake : snakeList) {
            snake.isNPC = true;
        }

        DEBUG_NPC_MOVEMENT_CIRCLING = true;
        
        application.getStompClient().setStompMessageListener(new StompMessageListener() {
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
            public void onGameInputMessageReceived(GameInputMessage msg) {

            }

            @Override
            public void onPlayerJoinedGameMessageReceived(PlayerJoinsGameMessage msg) {

            }
        });

        /*

        // setup everything related to the debug-mode
        if(AppSnakeFX.inDebugMode){

            // we gonna be gentle to the cpu
            if(DEBUG_LOWCORE){
                numPlayers = 3;
            }

            // we gonna stress test the cpu
            if(DEBUG_HARDCORE){
                numPlayers = 20;
            }

            // generate the players related to low or hardcore
            if(DEBUG_LOWCORE || DEBUG_HARDCORE){
                for (int i = 0; i < numPlayers; i++) {
                    int newY = i % 4 * 3 + 3;
                    int newX = i / 4 * 5 + 3;
                    snakeList.add (
                        new Snake (new Vector2(newX, newY),Color.color(Math.random(), Math.random(), Math.random()))
                    );
                }
            }

            // we want to play alone
            if(DEBUG_SINGLEPLAYER){
                snakeList.add ( new Snake ( new Vector2(5,5), Color.PURPLE));
            }

            // every snake is controlled by random keystrokes or some pattern
            if(DEBUG_EVERYBODY_NPC){
                for (Snake snake : snakeList) {
                    snake.isNPC = true;
                }
            }

            */

        /*
        // Normal mode
        } else {

        }
        */

        /*
        // setup everything related to the debug-mode
        if(AppSnakeFX.inDebugMode){

            // we gonna be gentle to the cpu
            if(DEBUG_LOWCORE){
                numPlayers = 3;
            }

            // we gonna stress test the cpu
            if(DEBUG_HARDCORE){
                numPlayers = 20;
            }

            // generate the players related to low or hardcore
            if(DEBUG_LOWCORE || DEBUG_HARDCORE){
                for (int i = 0; i < numPlayers; i++) {
                    int newY = i % 4 * 3 + 3;
                    int newX = i / 4 * 5 + 3;
                    snakeList.add (
                            new Snake (new Vector2(newX, newY),Color.color(Math.random(), Math.random(), Math.random()))
                    );
                }
            }

            // we want to play alone
            if(DEBUG_SINGLEPLAYER){
                snakeList.add ( new Snake ( new Vector2(5,5), Color.PURPLE));
            }

            // every snake is controlled by random keystrokes or some pattern
            if(DEBUG_EVERYBODY_NPC){
                for (Snake snake : snakeList) {
                    snake.isNPC = true;
                }
            }

            // Normal mode
        } else {
            // regular game-play
            // TODO: these values need to get retrieved from the backend
            numPlayers = 1;
            Color[] playerColors = {Color.PURPLE, Color.BLUE, Color.RED, Color.GREEN};
            for (int i = 0; i < numPlayers; i++) {
                snakeList.add ( new Snake ( new Vector2(0, 5 * i), playerColors[i]));
            }
        }
        */


        // add the initial UI to the scene
        Group root = new Group();
        Canvas canvas = new Canvas(config.width, config.height);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        // display the UI within the scene, center the stage on the users system
        currentStage.setScene(scene);

        // display the rendering canvas
        gc = canvas.getGraphicsContext2D();

        // initialize the food on the map
        // TODO: this thing is bugged -> generateFood();


        scene.setOnKeyPressed(this);

        // The game animation happens because of the timeline
        // every change happens in a new keyFrame (update-loop)
        // and the keyframe is generated by run
        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_TIME_AMOUNT), event -> update(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    // for meanwhile developing the game
    @Override
    public void postInitialize() {
        super.postInitialize();


    }

    /**
     * The update-loop of the game.
     * Gets called every TICK_TIME_AMOUNT until someone wins or some other kind of rule is fulfilled.
     * @param gc
     */
    private void update(GraphicsContext gc) {

        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", config.width / 3.5, config.width / 2);
            return;
        }

        drawBackground(gc);

        updateNPC();

        for (Snake snake : snakeList)
        {
            // calculate the current position for each snake
            for (int i = snake.body.size() - 1; i >= 1; i--) {
                snake.body.get(i).x = snake.body.get(i - 1).x;
                snake.body.get(i).y = snake.body.get(i - 1).y;
            }
            snake.head = snake.head.add(snake.currentDirection);
        }


        // visualize the game with its players (the snakes), the food, the score, etc.
        drawFood(gc);
        drawSnake(gc);
        drawScore(gc);

        // check if something happened
        checkGameOver();
        checkEatFood();

        drawDirections(gc);
        // drawIndices(gc);

    }

    Font arial10 = (Font.font("Arial", 10));

    private void drawIndices(GraphicsContext gc) {
        gc.setFont(arial10);
        int tileSize = config.tileSize;
        int halfTileSize = config.tileSize / 2;
        for (int y = 0; y < config.rows; y++) {
            for (int x = 0; x < config.columns; x++) {
                gc.fillText("(" + x + "," + y + ")", x * tileSize , y * tileSize + halfTileSize);
            }
        }
    }


    /**
     * Handles the input of the players or NPCs.
     * @param event - The input event
     */
    @Override
    public void handle(KeyEvent event) {
        KeyCode playerInput = event.getCode();

        // TODO: merge the following code!
        for (KeyCode firstPlayerInput : firstPlayerControls) {
            if (firstPlayerInput == playerInput) {
                Snake firstPlayer = snakeList.get(0);
                firstPlayer.currentDirection = getDirectionForInput(firstPlayer.currentDirection, firstPlayerInput);
            }
        }

        // TODO: merge the following code!
        for (KeyCode secondPlayerInput : secondPlayerControls) {
            if (secondPlayerInput == playerInput) {
                Snake secondPlayer = snakeList.get(1);
                secondPlayer.currentDirection = getDirectionForInput(secondPlayer.currentDirection,secondPlayerInput);
            }
        }

        if (playerInput == KeyCode.SPACE) {
            // Pause-Mode - this shoudnt be available in regular multiplayer
            // toggle the paused state
            paused = !paused;

            if(paused) { timeline.stop();}
            else { timeline.play();}

        }

        if(playerInput == KeyCode.ESCAPE){
            currentStage.close();
        }
        if(playerInput == KeyCode.R){
            currentStage.close();
            showLayout(Scenes.VIEW_GAME_CANVAS, ApplicationConstants.TITLE_CURRENT_GAME);
        }

        // increase the tick amount <=> faster game
        if(playerInput == KeyCode.PLUS){
            if(TICK_TIME_AMOUNT > 10){
                TICK_TIME_AMOUNT -= TICK_TIME_CHANGE_AMOUNT;
            }
        }

        // increase the tick amount <=> slower game
        if(playerInput == KeyCode.MINUS){
            TICK_TIME_AMOUNT += TICK_TIME_CHANGE_AMOUNT;
        }

        if(playerInput == KeyCode.PLUS || playerInput == KeyCode.MINUS){
            timeline.stop();
            timeline = null;
            timeline = new Timeline(new KeyFrame(Duration.millis(TICK_TIME_AMOUNT), event2 -> update(gc)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    // TODO: Aus dem GameController nehmen.
    private void updateNPC() {
        // cpu mechanism
        for (Snake snake : snakeList) {

            // if the current snake is marked as an npc
            if (snake.isNPC){

                if(DEBUG_NPC_MOVEMENT_CIRCLING){

                    // choose one of the four buttons as simulated player input
                    if(snake.currentInputButton == secondPlayerControls.length - 1){
                        snake.currentInputButton = 0;
                    } else {
                        snake.currentInputButton++;
                    }

                    // set the newly calculated direction based on the current input as the new direction
                    snake.currentDirection = getDirectionForInput(snake.currentDirection, secondPlayerControls[snake.currentInputButton]);
                }

                if(DEBUG_NPC_MOVEMENT_RANDOM_PATHS){

                    boolean nextDirectionInvalid;
                    Vector2 nextValidDirection = Vector2.ZERO;

                    do {

                        // We calculate a new direction for the snake to go to, randomly based on a simulated player input between W,A,S and D
                        Vector2 nextDirection = getDirectionForInput(snake.currentDirection, secondPlayerControls[RNG.getInstance().generate(0, firstPlayerControls.length)]);

                        // We get a reference to the current position of the snakes head
                        Vector2 currentPosition = new Vector2(snake.head);
                        Vector2 nextPosition = new Vector2(snake.head).add(nextDirection);

                        // we check if the snakes head + the next direction would result in a crash into the wall
                        boolean hitWallX = currentPosition.x + nextDirection.x < 0 || currentPosition.x + nextDirection.x > config.rows - 1;
                        boolean hitWallY = currentPosition.y + nextDirection.y < 0 || currentPosition.y + nextDirection.y > config.columns - 1;
                        boolean snakeHitItself = false;
                        boolean snakeHitAnotherSnake = false;

                        for (Vector2 bodyPartPosition : snake.body) {
                            if
                            (
                                    currentPosition.x + nextDirection.x == bodyPartPosition.x &&
                                            currentPosition.y + nextDirection.y == bodyPartPosition.y
                            )
                            {
                                snakeHitItself = true;
                            }
                        }

                        // if one of the cases happens, the position is invalid <=> recalculate another position
                        if (hitWallX || hitWallY || snakeHitItself || snakeHitAnotherSnake) {
                            nextDirectionInvalid = true;

                            // if this happens to often after a while, we can be sure the snake trapped / put itself in a box
                            // by the walls and / or its body - this forces the game to hang / get stuck because it tries to
                            // get a next valid position to go to - but there is none - a possible solution for this would be
                            // to introduce a https://de.wikipedia.org/wiki/Hamiltonkreisproblem
                            boxedSuicideCounter++;

                            if(boxedSuicideCounter >= boxedSuicideCounterMax){
                                snakeTrappedItself = true;
                                respawnSnake(snake);

                                boxedSuicideCounter = 20;
                                snakeTrappedItself = false;
                            }
                        } else {
                            nextDirectionInvalid = false;
                            nextValidDirection = nextDirection;

                            // there was a valid next position so we can reset this counter
                            boxedSuicideCounter = 0;
                            snakeTrappedItself = false;

                        }

                    } while (nextDirectionInvalid);

                    snake.currentDirection = nextValidDirection;

                }

            }
        }
    }

    //If NPC Snake wraps itself it gets spawned somewhere else
    private void respawnSnake(Snake snake){
        // despawn the current trapped snake
        int newLength = snake.body.size();
        snake.head = null;
        snake.body.clear();
        snake.body = null;
        snakeList.remove(snake);

        // respawn the snake somewhere else
        int newX = RNG.getInstance().generate(0, config.columns);
        int newY = RNG.getInstance().generate(0, config.rows);

        boolean positionInvalid = false;

        do {
            for (Snake snakeOnBoard : snakeList) {
                for (Vector2 bodyPart : snakeOnBoard.body) {
                    if (newX == bodyPart.x && newY == bodyPart.y) {
                        positionInvalid = true;
                        break;
                    }
                }
            }
        } while (positionInvalid);

        Snake newSnake = new Snake (new Vector2(newX, newY), snake.color, newLength);
        newSnake.isNPC = true;
        snakeList.add (newSnake);

    }

    private boolean snakeTrappedItself;
    private int boxedSuicideCounter = 0;
    private int boxedSuicideCounterMax = 20;

    private void drawDirections(GraphicsContext gc) {
        if(DEBUG_DRAW_DIRECTIONS){
            int tileSize = config.tileSize;
            int halfTileSize = tileSize / 2;
            for (Snake snake : snakeList) {
                Vector2 current = snake.head;
                Vector2 next = new Vector2(current).add(snake.currentDirection);

                gc.setLineWidth(10);
                gc.setStroke(Color.GREEN);
                gc.strokeLine(
                        current.x * tileSize + halfTileSize,
                        current.y * tileSize + halfTileSize,
                        next.x    * tileSize + halfTileSize,
                        next.y    * tileSize + halfTileSize
                );
            }
        }
    }

    /**
     * Draw the checker-board pattern to / as the background of the game.
     * @param gc
     */
    private void drawBackground(GraphicsContext gc) {
        // for each row ...
        for (int i = 0; i < config.rows; i++) {
            // for each column
            for (int j = 0; j < config.columns; j++) {
                if ((i + j) % 2 == 0) {
                    // if i+j is even - draw a slightly brighter grey tone
                    gc.setFill(Color.web("161718"));
                } else {
                    // if i+j is odd - draw a slightly darker grey tone
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
    private void generateFood() {

        boolean invalidFoodPosition = true;

        int newX;
        int newY;

        do {

            // calculate new positions
            newX = RNG.getInstance().generate(0, config.rows);
            newY = RNG.getInstance().generate(0,config.columns);

            for (Snake snake : snakeList) {
                if(snake.head.x != newX && snake.head.y == newY){
                    invalidFoodPosition = false;
                }
                for (Vector2 bodyPart : snake.body) {
                    if(bodyPart.x != newX && bodyPart.y != newY){
                        invalidFoodPosition = false;
                    }
                }
            }

        } while (invalidFoodPosition);

        Food food = new Food(
                new Image(FOOD_IMAGE_PATHS[RNG.getInstance().generate(0, FOOD_IMAGE_PATHS.length)]),
                new Vector2(newX, newY)
        );

        foodList.add(food);

    }

    /**
     * Draw the food, which has been generated bevor
     * @param gc
     */
    private void drawFood(GraphicsContext gc) {
        for (Food food : foodList) {
            gc.drawImage(
                    food.drawable,
                    food.getPosition().x * config.tileSize,
                    food.getPosition().y * config.tileSize,
                    config.tileSize,
                    config.tileSize
            );
        }
    }

    /**
     * Draw the snake and their body-parts based on their position
     * @param gc
     */
    private void drawSnake(GraphicsContext gc) {

        for (Snake snake : snakeList) {

            // draw the head of the snake
            gc.setFill(snake.color.brighter());
            gc.fillRoundRect(
                    snake.head.getX() * config.tileSize,
                    snake.head.getY() * config.tileSize,
                    config.tileSize - 1,
                    config.tileSize - 1,
                    35,
                    35
            );

            // draw the body of the snake
            gc.setFill(snake.color);
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
        }
    }

    /**
     * Check if the game is finished.
     */
    public void checkGameOver() {
        // check for wall collision
        for (Snake snake : snakeList) {

            if (snake.head.x < 0 || snake.head.y < 0 ||
                    snake.head.x > config.rows -1 ||
                    snake.head.y > config.columns -1) {
                gameOver = true;
            }

            // destroy itself
            for (int i = 1; i < snake.body.size(); i++) {
                if (snake.head.x == snake.body.get(i).getX() && snake.head.getY() == snake.body.get(i).getY()) {
                    gameOver = true;
                    break;
                }
            }
        }

        // Instead of going game over when hitting a wall you can also use a wallteleport
        /*
        public checkWallTeleport();
        ...
        public void checkWallTeleport() {

            // check for wall collision
            for (Snake snake : snakeList) {

                if (snake.head.x < 0){
                    snake.head.x = config.rows;
                }
                else if (snake.head.y < 0){
                    snake.head.y = config.rows;
                }
                else if (snake.head.x > config.rows){
                    snake.head.x = 0;
                }
                else if (snake.head.y > config.columns) {
                    snake.head.y = 0;
                }
        */


        // check for snake-collision
        for (Snake a : snakeList) {
            for (Snake b : snakeList) {
                if(a.head != b.head && a.head.x == b.head.x && a.head.y == b.head.y){
                    gameOver = true;
                }

                for (Vector2 abody : a.body) {
                    for (Vector2 bbody : b.body) {
                        // check if abody is not actually abody again
                        // check if x of snake a is x of snake b
                        // check if y of snake a is y of snake b
                        if(abody != bbody && abody.x == bbody.x && abody.y == bbody.y
                                && abody.x != -1 && abody.y != -1 && bbody.x != -1 && bbody.y != -1 ){
                            gameOver = true;
                            // System.out.println("Schlange A: " + abody);
                           // System.out.println("Schlange B: " + bbody);
                        }
                    }
                }
            }
        }


    }

    private void checkEatFood() {
        for (Snake snake : snakeList) {
            for (Food food : foodList) {
                if (snake.head.getX() == food.getPosition().x && snake.head.getY() == food.getPosition().y) {
                    foodList.remove(food);
                    snake.body.add(new Vector2(-1, -1));
                    generateFood();
                    score += 5;
                }
            }
        }
    }

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 35)); // Find funny font
        gc.fillText("Score: " + score, 10, 35);
    }

    private Vector2 getDirectionForInput(Vector2 currentDirection , KeyCode playerInput) {

        if (playerInput == firstPlayerControls[0] || playerInput == secondPlayerControls[0]) {
            if (currentDirection != Vector2.DOWN) {
                currentDirection = Vector2.UP;
            }
        } else if (playerInput == firstPlayerControls[1] || playerInput == secondPlayerControls[1]) {
            if (currentDirection != Vector2.RIGHT) {
                currentDirection = Vector2.LEFT;
            }
        } else if (playerInput == firstPlayerControls[2] || playerInput == secondPlayerControls[2]) {
            if (currentDirection != Vector2.UP) {
                currentDirection = Vector2.DOWN;
            }
        } else if (playerInput == firstPlayerControls[3] || playerInput == secondPlayerControls[3]) {
            if (currentDirection != Vector2.LEFT) {
                currentDirection = Vector2.RIGHT;
            }
        }

        return currentDirection;

    }

}
