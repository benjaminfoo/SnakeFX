package de.ostfalia.snakecore.controller.login;

import de.ostfalia.snakecore.ApplicationConstants;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.controller.Scenes;
import de.ostfalia.snakecore.task.LoginTask;
import de.ostfalia.snakecore.task.RegisterTask;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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

        debugButton.setOnAction(clickEvent -> showLayoutInNewWindow(Scenes.VIEW_DEBUG, ApplicationConstants.TITLE_DEBUG_CHOOSER));

        // this disables the login / register button if no input for name / password has been provided
        btnLogin.disableProperty().bind(
                Bindings.and(
                        Bindings.isEmpty(tfName.textProperty()),
                        Bindings.isEmpty(tfPassword.textProperty())
                )
        );

        btnRegister.disableProperty().bind(btnLogin.disableProperty());

        tfServerURL.setText(ProjectEndpoints.BASE_URL);
        ProjectEndpoints.BASE_URL = tfServerURL.getText().trim();
        ProjectEndpoints.resetURLs();
    }

    private void executeLogin() {
        String username = tfName.getText();
        String password = tfPassword.getText();

        username = username.trim();

        LoginTask loginTask = new LoginTask(username, password);

        loginTask.setOnRunning(event -> {
            lblStatus.setText("Connecting ...");
        });

        loginTask.setOnFailed(event -> {
            System.out.println("Login not successful for user: " + loginTask.getValue());
            lblStatus.setText("Error on login!");
        });

        loginTask.setOnSucceeded(event -> {
            System.out.println("Login successful for user: " + loginTask.getValue());
            lblStatus.setText("Login succesful!");
        });

        // new Thread(loginTask).start();
        showLayout(Scenes.VIEW_HOMESCREEN, ApplicationConstants.TITLE_HOMESCREEN);
    }

    public void executeRegistration(){

        String username = tfName.getText();
        String password = tfPassword.getText();

        username = username.trim();

        new RegisterTask().register(username, password);
    }


}