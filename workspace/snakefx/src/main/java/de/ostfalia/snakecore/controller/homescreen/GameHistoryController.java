package de.ostfalia.snakecore.controller.homescreen;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.controller.BaseController;
import de.ostfalia.snakecore.model.Spielstand;
import de.ostfalia.snakecore.task.GetHistorieTask;
import de.ostfalia.snakecore.view.SpielstandCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * @author Benjamin Wulfert
 * <p>
 * The GameHistoryController handles the retrieval and visualization of played games.
 * It lists its contents within a listview.
 */
public class GameHistoryController extends BaseController {

    @FXML
    Button back;

    @FXML
    ListView<Spielstand> gameHistoryList;

    /**
     * Initialize gets called when the Controller is loaded by the JavaFX's-FXMLLoader
     */
    public void initialize() {
        super.initialize();
        back.setOnAction(onclick -> showHomeScreen());

        List<Spielstand> spielstaende = FXCollections.emptyObservableList();
        try {
            spielstaende = new GetHistorieTask().getSpielstaende();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        gameHistoryList.setCellFactory(listView -> new SpielstandCell());

        gameHistoryList.setItems(FXCollections.observableList(spielstaende));
    }

}
