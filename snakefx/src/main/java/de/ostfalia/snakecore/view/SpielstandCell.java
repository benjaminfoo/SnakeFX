package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.Spielstand;
import javafx.scene.control.ListCell;

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