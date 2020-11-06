package de.ostfalia.teamx.controller.homescreen;

import de.ostfalia.teamx.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Benjamin Wulfert
 *
 * The GameHistoryController handles the retrieval and visualization of played games.
 * It lists its contents within a listview.
 */
public class GameHistoryController extends BaseController {

    @FXML
    Button back, close;

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize(){
        super.initialize();
        close.setOnAction(onclick -> closeStage());
    }

}
