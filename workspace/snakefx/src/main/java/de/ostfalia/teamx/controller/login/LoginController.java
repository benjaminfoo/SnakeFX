package de.ostfalia.teamx.controller.login;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.model.Spieler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    TextField tfName, tfPassword;

    @FXML
    Button btnLogin, btnRegister;

    @FXML
    Label lblStatus;

    // this method gets called by mapping it in the login_view.fxml
    public void initialize(){
        btnLogin.setOnAction(clickEvent -> {

        });

        btnRegister.setOnAction(clickEvent -> {
            executeRegistration();
        });
    }

    public void executeRegistration(){
        System.out.println("login");

        String username = tfName.getText();
        String password = tfPassword.getText();

        Spieler neuerSpieler = new Spieler(-1, username, password);
        Gson gson = new Gson();

        try {
            System.out.println("Posting: " + gson.toJson(neuerSpieler));
            HttpResponse<String> res = Unirest
                    .post(ProjectEndpoints.HOST_URL_REGISTER)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(neuerSpieler))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }


}
