package de.ostfalia.snakecore.view;

import de.ostfalia.snakecore.model.Spielstand;
import de.ostfalia.snakecore.model.SpielstandErgebnis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpielstandData {

    @FXML
    HBox hbox;

    @FXML
    private Label text1;

    @FXML
    private Label text2;

    public SpielstandData() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("cell_gamehistorie.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Spielstand spielstand) {
        // Convert long to date instance
        long val = spielstand.date;
        Date date = new Date(val);

        // Format date
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy - HH:MM");
        String dateText = df2.format(date);

        text1.setText("Spielstand Nr. " + spielstand.id + " - " + dateText);

        StringBuilder stringBuilder = new StringBuilder();
        for (SpielstandErgebnis e : spielstand.ergebnisse) {
            stringBuilder.append("Spieler: " + e.spieler.getName() + " - Score: " + e.score + "\n");
        }
        text2.setText(stringBuilder.toString());
    }

    public HBox getBox() {
        return hbox;
    }
}