package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.Spielstand;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Spielstand> {
    @Override
    public void updateItem(Spielstand spielstand, boolean empty) {
        super.updateItem(spielstand, empty);
        if (spielstand != null) {
            Data data = new Data();
            data.setInfo(spielstand);
            setGraphic(data.getBox());
        }
    }
}