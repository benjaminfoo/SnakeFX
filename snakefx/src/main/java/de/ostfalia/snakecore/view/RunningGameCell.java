package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.RunningGame;
import javafx.scene.control.ListCell;

public class RunningGameCell extends ListCell<RunningGame> {

    @Override
    public void updateItem(RunningGame runningGame, boolean empty) {
        super.updateItem(runningGame, empty);
        if (runningGame != null) {
            RunningGameData runningGameData = new RunningGameData();
            runningGameData.setInfo(runningGame);
            setGraphic(runningGameData.getBox());
        }
    }

}