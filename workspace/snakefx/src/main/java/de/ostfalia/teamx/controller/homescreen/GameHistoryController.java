package de.ostfalia.teamx.controller.homescreen;

import de.ostfalia.teamx.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameHistoryController extends BaseController {

    @FXML
    Button back, close;

    public void initialize(){
        super.initialize();
        close.setOnAction(onclick -> closeStage());
    }

}
