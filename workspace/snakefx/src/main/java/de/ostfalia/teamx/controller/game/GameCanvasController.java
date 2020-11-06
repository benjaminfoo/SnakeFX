package de.ostfalia.teamx.controller.game;

import de.ostfalia.teamx.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Benjamin Wulfert
 *
 * The GameCanvasController manages the state of the game canvas.
 * It does not contain logic for rendering, updating or anything - just the javafx side of things.
 */
public class GameCanvasController extends BaseController {

    @FXML
    Label statusLabel;

    // gets called once upon the controller start
    public void initialize(){
        super.initialize();
        statusLabel.setText("Hello World!");
    }

}
