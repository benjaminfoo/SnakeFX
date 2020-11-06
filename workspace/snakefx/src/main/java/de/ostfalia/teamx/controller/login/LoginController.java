package de.ostfalia.teamx.controller.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ApplicationConstants;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.model.Spieler;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class LoginController {

    @FXML
    TextField tfName, tfPassword, tfServerURL;

    @FXML
    Button btnLogin, btnRegister;

    @FXML
    Label lblStatus;

    // this method gets called by mapping it in the login_view.fxml
    public void initialize(){
        btnLogin.setOnAction(clickEvent -> {
            executeLogin();
        });

        btnRegister.setOnAction(clickEvent -> {
            executeRegistration();
        });

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

        Spieler neuerSpieler = new Spieler(-1, username, password);
        Gson gson = new Gson();

        try {
            System.out.println("Login: " + gson.toJson(neuerSpieler));
            HttpResponse<String> res = Unirest
                    .post(ProjectEndpoints.HOST_URL_API_REGISTER)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(neuerSpieler))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public void executeRegistration(){

        String username = tfName.getText();
        String password = tfPassword.getText();

        Spieler neuerSpieler = new Spieler(-1, username, password);
        Gson gson = new Gson();

        try {
            System.out.println("Register new player: " + gson.toJson(neuerSpieler));
            HttpResponse<String> res = Unirest
                    .post(ProjectEndpoints.HOST_URL_API_REGISTER)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(neuerSpieler))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }


}
