package de.ostfalia.teamx.snakorino.controller;

import de.ostfalia.teamx.AppSnakeFX;
import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.controller.Scenes;
import de.ostfalia.teamx.snakorino.model.Config;
import de.ostfalia.teamx.snakorino.model.Direction;
import de.ostfalia.teamx.snakorino.model.Snake;
import de.ostfalia.teamx.snakorino.model.Vector2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Leonard Reidel
 * @author Benjamin Wulfert
 *
 * The GameCanvasController manages the state of the game canvas - it also contains the game-logic for realizing
 * the snake-game.
 */
public class GameController extends BaseController {

    // the configuration in order to setup a game
    // TODO: this needs to get connected with a SpielDefinition, i guess
    private Config config = new Config();

    private static final String[] FOODS_IMAGE = new String[]{
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

    // The GraphicsContext used to draw the background, the snake-shapes and the food / powerups
    private GraphicsContext gc;

    // the timeline which is (ab-)used to realize some kind of update-loop
    private Timeline timeline;

    // the amount of milliseconds each game-tick needs in order to update the games state
    public double TICK_TIME_AMOUNT = 130;

    // FOOD STUFF - Extract to class Food.java
    // The image of the current food
    private Image foodImage;
    private int foodX;
    private int foodY;
    // FOOD STUFF

    // States
    private boolean gameOver;
    private boolean paused;

    // SNAKE STUFF - REFACTOR TO THE SNAKE CLASS
    // related to one player
    // private Snake snake = new Snake();
    // the score of the first player
    private int score = 0;
    // SNAKE STUFF

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

    private static final boolean DEBUG_HARDCORE = false;
    private static final boolean DEBUG_NPC_RANDOM_PATHS = false
            ;
    private static final boolean DEBUG_NPC_CIRCLING = true;
    private static final boolean DEBUG_EVERYBODY_NPC = true;


    @Override
    public void postInitialize() {
        super.postInitialize();

        // This is a debug setup!
        if(AppSnakeFX.inDebugMode){

            if(DEBUG_HARDCORE){
                // we gonna stress test this b!atch
                numPlayers = 20;
                for (int i = 0; i < numPlayers; i++) {

                    int newY = i % 4 * 3 + 3;
                    int newX = i / 4 * 5 + 3;

                    snakeList.add (
                            new Snake (
                                    new Vector2(newX, newY),
                                    Color.color(Math.random(), Math.random(), Math.random())
                            )
                    );
                }

            }

            if(DEBUG_NPC_RANDOM_PATHS){
                numPlayers = 2;
                Color[] playerColors = {Color.PURPLE, Color.BLUE, Color.RED, Color.GREEN};
                for (int i = 0; i < numPlayers; i++) {
                    snakeList.add ( new Snake ( new Vector2(0, 5 * i), playerColors[i]));
                }
            }

            if(DEBUG_EVERYBODY_NPC){
                for (int i = 0; i < snakeList.size(); i++) {
                    snakeList.get(i).isNPC = true;
                }
            }

        }

        if(!AppSnakeFX.inDebugMode){
            // TODO: these values need to get retrieved from the backend
            numPlayers = 1;
            Color[] playerColors = {Color.PURPLE, Color.BLUE, Color.RED, Color.GREEN};
            for (int i = 0; i < numPlayers; i++) {
                snakeList.add ( new Snake ( new Vector2(0, 5 * i), playerColors[i]));
            }
        }


        // add the initial UI to the scene
        Group root = new Group();
        Canvas canvas = new Canvas(config.width, config.width);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        // display the UI within the scene, center the stage on the users system
        currentStage.setScene(scene);
        currentStage.centerOnScreen();

        gc = canvas.getGraphicsContext2D();

        // converted object to lambda using intellij's refactor suggestion
        // this lambda provides a players input
        scene.setOnKeyPressed(event -> {

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

            } else if(playerInput == KeyCode.R){
                currentStage.close();
                showLayout(Scenes.VIEW_GAME_CANVAS, ApplicationConstants.TITLE_CURRENT_GAME);
            }

        });

        // initialize the food on the map
        generateFood();

        // The game animation happens because of the timeline
        // every change happens in a new keyFrame (update-loop)
        // and the keyframe is generated by run
        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_TIME_AMOUNT), event -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    private Direction getDirectionForInput(Direction currentDirection , KeyCode playerInput) {

        if (playerInput == firstPlayerControls[0] || playerInput == secondPlayerControls[0]) {
            if (currentDirection != Direction.down) {
                currentDirection = Direction.up;
            }
        } else if (playerInput == firstPlayerControls[1] || playerInput == secondPlayerControls[1]) {
            if (currentDirection != Direction.right) {
                currentDirection = Direction.left;
            }
        } else if (playerInput == firstPlayerControls[2] || playerInput == secondPlayerControls[2]) {
            if (currentDirection != Direction.up) {
                currentDirection = Direction.down;
            }
        } else if (playerInput == firstPlayerControls[3] || playerInput == secondPlayerControls[3]) {

            if (currentDirection != Direction.left) {
                currentDirection = Direction.right;
            }
        }

        return currentDirection;

    }

    /**
     * The update-loop of the game.
     * Gets called every TICK_TIME_AMOUNT until someone wins or some other kind of rule is fulfilled.
     * @param gc
     */
    private void run(GraphicsContext gc) {
        
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", config.width / 3.5, config.width / 2);
            return;
        }

        // visualize the game with its players (the snakes), the food, the score, etc.
        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();

        // cpu mechanism
        for (Snake snake : snakeList) {

            // if the current snake is marked as an npc
            if (snake.isNPC){

                if(DEBUG_NPC_CIRCLING){

                    // choose one of the four buttons as simulated player input
                    if(snake.currentInputButton == secondPlayerControls.length - 1){
                        snake.currentInputButton = 0;
                    } else {
                        snake.currentInputButton++;
                    }

                    // set the newly calculated direction based on the current input as the new direction
                    snake.currentDirection = getDirectionForInput(snake.currentDirection, secondPlayerControls[snake.currentInputButton]);
                }

            }
        }

        for (Snake snake : snakeList)
        {
            // calculate the current position for each snake
            for (int i = snake.body.size() - 1; i >= 1; i--) {
                snake.body.get(i).x = snake.body.get(i - 1).x;
                snake.body.get(i).y = snake.body.get(i - 1).y;
            }

            switch (snake.currentDirection) {
                case right:
                    snake.moveright();
                    break;
                case left:
                    snake.moveLeft();
                    break;
                case up:
                    snake.moveUp();
                    break;
                case down:
                    snake.moveDown();
                    break;
            }
        }

        checkGameOver();
        checkEatFood();
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
                gc.fillRect(i * config.square_size, j * config.square_size,
                        config.square_size, config.square_size);
            }
        }
    }

    /**
     * Generate the food for the players within the game map.
     * TODO - maybe we shouldnt generate food below a snake?
     */
    private void generateFood() {

        boolean validPositionForNextFoodGeneration = false;

        do {

            foodX = (int) (Math.random() * config.rows);
            foodY = (int) (Math.random() * config.columns);

            for (Snake snake : snakeList) {
                Vector2 head = snake.body.get(0);
                if (head.getX() != foodX && head.getY() != foodY) { // food can appear below snake (snake shown above food)
                    validPositionForNextFoodGeneration = true;
                }
            }

            foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);

        } while (!validPositionForNextFoodGeneration);

    }

    /**
     * Draw the food, which has been generated bevor
     * @param gc
     */
    private void drawFood(GraphicsContext gc) {
        gc.drawImage(
                foodImage,
                foodX * config.square_size,
                foodY * config.square_size,
                config.square_size,
                config.square_size
        );
    }

    /**
     * Draw the snake and their body-parts based on their position
     * @param gc
     */
    private void drawSnake(GraphicsContext gc) {

        for (Snake snake : snakeList) {

            gc.setFill(snake.color);

            gc.fillRoundRect(
                    snake.head.getX() * config.square_size,
                    snake.head.getY() * config.square_size,
                    config.square_size - 1,
                    config.square_size - 1,
                    35,
                    35
            );

            for (int i = 1; i < snake.body.size(); i++) {
                gc.fillRoundRect(
                        snake.body.get(i).getX() * config.square_size,
                        snake.body.get(i).getY() * config.square_size,
                        config.square_size - 1,
                        config.square_size - 1,
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
                    snake.head.x * config.square_size >= config.width ||
                    snake.head.y * config.square_size >= config.width) {
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
            if (snake.head.getX() == foodX && snake.head.getY() == foodY) {
                snake.body.add(new Vector2(-1, -1));
                generateFood();
                score += 5;
            }
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 35)); // Find funny font
        gc.fillText("Score: " + score, 10, 35);
    }


}
