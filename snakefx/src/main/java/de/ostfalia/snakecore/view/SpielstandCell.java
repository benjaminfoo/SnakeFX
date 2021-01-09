package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.Spielstand;
import javafx.scene.control.ListCell;

/**
 * @author Benjamin Wulfert
 *
 * The SpielstandCell is an extension class of the javafx-listview cells (the items within a listview).
 * This class is used to customize the data visualization within the ui's listviews.
 */
public class SpielstandCell extends ListCell<Spielstand> {

    @Override
    public void updateItem(Spielstand spielstand, boolean empty) {
        super.updateItem(spielstand, empty);
        if (spielstand != null) {
            SpielstandData spielstandData = new SpielstandData();
            spielstandData.setInfo(spielstand);
            setGraphic(spielstandData.getBox());
        }
    }

}