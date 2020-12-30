package de.ostfalia.teamx;

import de.ostfalia.snakecore.AppSnakeFX;
import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.SnakeServer.SnakeServerApplication;
import de.ostfalia.snakecore.controller.homescreen.HomescreenController;
import de.ostfalia.snakecore.controller.homescreen.NewGameController;
import de.ostfalia.snakecore.controller.login.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Benjamin Wulfert
 *
 * This class isn't a traditional jUnit testsuite - the module containing this class also contains a reference
 * to all subprojects: snakeCore, snakeFx, snakeServer
 *
 * This class initializes a server-instance and two client instances.
 * The first client automatically creates a new game, while the other one connects to it via the lobby ui.
 *
 */
public class EnvironmentTest_ServerTwoClients_Playing extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Start the application-server
        ConfigurableApplicationContext run = new SpringApplicationBuilder().sources(SnakeServerApplication.class).run();


        // Start the first client
        Platform.runLater(() -> {
            try {
                AppSnakeFX appSnakeFX = new AppSnakeFX(ApplicationConstants.TEST_USER_ONE_NAME, ApplicationConstants.TEST_USER_ONE_PASS);
                Stage windowStage = new Stage();
                appSnakeFX.start(windowStage);

                Thread thread = new Thread(() -> {
                    try {

                        // set the first window to the left side of the screen
                        windowStage.setX(50);

                        System.out.println("Testing login ...");
                        LoginController currentController = (LoginController) appSnakeFX.initializedController.get(LoginController.class);
                        currentController.executeLogin();

                        Thread.sleep(4000);

                        System.out.println("Test enter new game  ...");
                        HomescreenController homescreenController = (HomescreenController) appSnakeFX.initializedController.get(HomescreenController.class);
                        Platform.runLater(() -> homescreenController.newGame.fire());

                        Thread.sleep(500);

                        System.out.println("Test new game creation ...");
                        NewGameController newGameController = (NewGameController) appSnakeFX.initializedController.get(NewGameController.class);
                        Platform.runLater(() -> newGameController.newGame.fire());

                        Thread.sleep(2000);
                        Platform.runLater(() -> ((HomescreenController) appSnakeFX.initializedController.get(HomescreenController.class)).activeGames.requestFocus());

                        Thread.sleep(200);
                        Platform.runLater(() -> ((HomescreenController) appSnakeFX.initializedController.get(HomescreenController.class)).activeGames.getSelectionModel().select(0));

                        Thread.sleep(2500);
                        Platform.runLater(() -> homescreenController.simulateChatMessage("Starting the game now!"));
                        Platform.runLater(() -> ((HomescreenController) appSnakeFX.initializedController.get(HomescreenController.class)).adminStartGame.fire());


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        // start the second client
        Platform.runLater(() -> {
            try {
                AppSnakeFX appSnakeFX = new AppSnakeFX(ApplicationConstants.TEST_USER_TWO_NAME, ApplicationConstants.TEST_USER_TWO_PASS);
                Stage windowStageTwo = new Stage();
                appSnakeFX.start(windowStageTwo);

                Thread thread = new Thread(() -> {
                    try {

                        // set the second window to the right side of the screen
                        windowStageTwo.setX(950);

                        System.out.println("Testing login ...");
                        LoginController currentController = (LoginController) appSnakeFX.initializedController.get(LoginController.class);
                        currentController.executeLogin();

                        Thread.sleep(4000);

                        System.out.println("Testing game selection ... ");
                        HomescreenController homescreenController = (HomescreenController) appSnakeFX.initializedController.get(HomescreenController.class);
                        homescreenController.activeGames.getSelectionModel().select(0);

                        Thread.sleep(2000);

                        System.out.println("Testing game selection ... ");
                        Platform.runLater(() -> homescreenController.simulateChatMessage("Sure! I'll join your game =)!"));

                        Thread.sleep(100);
                        Platform.runLater(() -> homescreenController.activeGames.requestFocus());

                        Thread.sleep(100);
                        Platform.runLater(() -> homescreenController.activeGames.getFocusModel().focus(0));

                        Thread.sleep(100);
                        Platform.runLater(() -> homescreenController.activeGames.getSelectionModel().selectFirst());

                        Thread.sleep(100);
                        Platform.runLater(() -> homescreenController.joinGame.requestFocus());

                        Thread.sleep(100);
                        Platform.runLater(() -> homescreenController.joinGame.fire());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


}
