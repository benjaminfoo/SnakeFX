package de.ostfalia.teamx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The LoginController is responsible for allowing a user to connect with the server with
 * credentials like username and a corresponding password.
 *
 * Its also responsible to switch between the login and the register scene.
 * In order to switch to the login-view state call login()
 * In order to switch to the register-view state call register()
 *
 * Maybe we could use a statemachine for this or we duplicate the controller and the login_view.fxml
 *
 *
 */
public class LoginController {

    // Status des Controllers
    public enum State {

        // in diesem Zustand wird das UI für
        LOGIN,

        REGISTER
    }

    public State current = State.LOGIN;

    @FXML
    TextField tfName, tfPassword;

    @FXML
    Button btnLogin, btnRegister;

    @FXML
    Label lblStatus;

    // this method gets called by mapping it in the login_view.fxml
    public void initialize(){
        btnLogin.setOnAction(clickEvent -> switchToLoginState());
        btnRegister.setOnAction(clickEvent -> switchToRegisterState());

        if(current == State.LOGIN){
            switchToLoginState();
        }

        if(current == State.REGISTER){
            switchToRegisterState();
        }
    }

    public void switchToLoginState(){
        current = State.LOGIN;

        tfName.setPromptText("Benutzername");
        tfPassword.setPromptText("Passwort");

        btnLogin.setText("Anmelden");
        btnRegister.setText("Registrieren");

        lblStatus.setText("Anmeldung");
    }


    public void switchToRegisterState(){
        current = State.REGISTER;

        tfName.setPromptText("Neuer Benutzername");
        tfPassword.setPromptText("Neues Passwort");

        btnLogin.setText("Erstellen");
        btnRegister.setText("Abbrechen");

        lblStatus.setText("Erstelle neuen Account");
    }

    public void login() {
        tfName.setPromptText("Benutzername");
        tfPassword.setPromptText("Passwort");

        String name = tfName.getText().trim();
        String password = tfPassword.getText();

        lblStatus.setText("Login ...");
    }

    public void register(){
        String name = tfName.getText().trim();
        String password = tfPassword.getText();

        System.out.println("Name: " + name + " | Password: " + password);

        boolean inputValid = (!name.isEmpty() && !password.isEmpty());

        if(inputValid){
            switchToLoginState();
        }
    }

}
