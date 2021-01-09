package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.RunningGame;
import javafx.scene.control.ListCell;

/**
 * @author Benjamin Wulfert
 *
 * The RunningGameCell is an extension class of the javafx-listview cells (the items within a listview).
 * This class is used to customize the data visualization within the ui's listviews.
 */
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