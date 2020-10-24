package de.ostfalia.teamx.controller.login;

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

        });
    }


}
