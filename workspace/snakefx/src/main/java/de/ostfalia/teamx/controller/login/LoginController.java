package de.ostfalia.teamx.controller.login;

import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.controller.BaseController;
import de.ostfalia.teamx.task.LoginTask;
import de.ostfalia.teamx.task.RegisterTask;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginController extends BaseController {

    @FXML
    TextField tfName, tfPassword, tfServerURL;

    @FXML
    Button btnLogin, btnRegister;

    @FXML
    Label lblStatus;

    // this method gets called by mapping it in the login_view.fxml
    public void initialize(){
        super.initialize();

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

        username = username.trim();

        new LoginTask().login(username, password);
    }

    public void executeRegistration(){

        String username = tfName.getText();
        String password = tfPassword.getText();

        username = username.trim();

        new RegisterTask().register(username, password);
    }


}
