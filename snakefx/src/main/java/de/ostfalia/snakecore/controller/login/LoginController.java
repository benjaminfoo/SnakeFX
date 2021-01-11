package de.ostfalia.snakecore.controller.login;

import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.UserConfig;
import de.ostfalia.snakecore.task.LoginTask;
import de.ostfalia.snakecore.task.RegisterTask;
import de.ostfalia.snakecore.ws.model.PlayerMessage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;


/**
 * @author Benjamin Wulfert
 *
 * The LoginController is responsible the login- or registration-process.
 * The LoginController is the first user interface which the user has access to in the application.
 */
public class LoginController extends BaseController {

    @FXML
    TextField tfName, tfPassword, tfServerURL;

    @FXML
    Button btnLogin, btnRegister, debugButton;

    @FXML
    Label lblStatus;

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize(){
        super.initialize();

        btnLogin.setOnAction(clickEvent -> {
            executeLogin();
        });

        btnRegister.setOnAction(clickEvent -> {
            executeRegistration();
        });

        tfName.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                tfPassword.requestFocus();
            }
        });

        // reuse the onKeyReleased listener from the name
        tfPassword.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                btnLogin.fire();
            }
        });

        debugButton.setOnAction(clickEvent -> showLayoutInNewWindow(Scenes.VIEW_DEBUG, ApplicationConstants.TITLE_DEBUG_CHOOSER));

        // this disables the login / register button if no input for name / password has been provided
        btnLogin.disableProperty().bind(
                Bindings.and(
                        Bindings.isEmpty(tfName.textProperty()),
                        Bindings.isEmpty(tfPassword.textProperty())
                )
        );

        btnRegister.disableProperty().bind(btnLogin.disableProperty());

        tfServerURL.setText(ProjectEndpoints.URL_BASE);
        ProjectEndpoints.URL_BASE = tfServerURL.getText().trim();
        ProjectEndpoints.resetURLs();

    }

    @Override
    public void postInitialize() {
        super.postInitialize();

        List<String> startParams = application.getStartParams();
        if(startParams != null){
            application.setUserConfig(new UserConfig(startParams.get(0), startParams.get(1), null));
        }

        if(application.getUserConfig() != null){
            tfName.setText(application.getUserConfig().getUserName());
            tfPassword.setText(application.getUserConfig().getPass());
        }
    }

    public void executeLogin() {
        System.out.println("Logging in ...");

        String username = tfName.getText();
        String password = tfPassword.getText();

        username = username.trim();

        LoginTask loginTask = new LoginTask(username, password);

        loginTask.setOnRunning(event -> {
            lblStatus.setStyle("-fx-text-fill: #000000");
            lblStatus.setText("Connecting ...");
        });

        loginTask.setOnFailed(event -> {
            System.out.println("Login not successful for user: " + loginTask.getValue());
            lblStatus.setStyle("-fx-text-fill: #FF0000");
            lblStatus.setText("Error on login!");
        });

        loginTask.setOnCancelled(event -> {
            lblStatus.setStyle("-fx-text-fill: #000000");
            lblStatus.setText("Error on login!");
        });

        loginTask.setOnSucceeded(event -> {
            Spieler value = loginTask.getValue();

            lblStatus.setStyle("-fx-text-fill: #FFFFFF");
            System.out.println("Login successful for user: " + value);
            lblStatus.setText("Login succesful!");

            // When everything worked, reference the application with the logged in user

            // TODO: retrieve JWT Token from server when logging in
            String jwtToken = "NONE";
            application.setUserConfig(new UserConfig(value.name, value.pass, jwtToken));

            // connect the stomp client to the stomp endpoint
            // application.getStompClient().connect("ws://localhost:8080/snakeserver", () -> {
            application.getStompClient().connect(ProjectEndpoints.URL_STOMP_BASE, () -> {

                // register the player on the lobby
                application.getStompClient().sendNewPlayerMessage(new PlayerMessage(
                        new Spieler(-1337L, application.getUserConfig().getUserName(), application.getUserConfig().getPass())
                ));

                // execute on the ui-thread
                Platform.runLater(() -> {
                    // Switch to the homescreen
                    showHomeScreen();
                });

            });


        });

        // execute the thread
        new Thread(loginTask).start();
    }

    public void executeRegistration(){

        String username = tfName.getText();
        String password = tfPassword.getText();

        username = username.trim();

        new RegisterTask().register(username, password);
    }


}
