package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.RunningGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Benjamin Wulfert
 *
 * The RunningGameData is a pojo class which contains references to the inflated layout for a listCell (RunningGameCell).
 * This allows the data setting for a cell based on listItem entries.
 */
public class RunningGameData {

    @FXML
    HBox hbox;

    @FXML
    private Label text1;

    @FXML
    private Label text2;

    @FXML
    private Label text3;

    public RunningGameData() {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("cell_runningame.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(RunningGame runningGame) {

        text1.setText(runningGame.spielDefinition.getNameOfTheGame());

        text2.setText(runningGame.activeClients.size() + "/" + runningGame.spielDefinition.getNumberOfPlayer());

        text3.setText(
                "Admin: " + runningGame.admin.getName() + " | Anz. PowerUps: " + runningGame.spielDefinition.getMaxNumberOfPowerUps() + "\n" +
                "Map-Size: " + runningGame.spielDefinition.getMapWidth() + "x" + runningGame.spielDefinition.getMapHeight() + "\n" +
                "Stomp-Path: " + runningGame.getStompPath()
        );
    }

    public HBox getBox() {
        return hbox;
    }
}