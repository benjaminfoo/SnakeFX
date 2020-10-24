package de.ostfalia.teamx.controller.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The GameCanvasController manages the state of the game canvas.
 * It does not contain logic for rendering, updating or anything - just the javafx side of things.
 */
public class GameCanvasController {

    @FXML
    Label statusLabel;

    // gets called once upon the controller start
    public void initialize(){
        statusLabel.setText("Hello World!");
    }

}
