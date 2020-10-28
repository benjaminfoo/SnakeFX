package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Benjami Wulfert
 * Eine Spielregel definiert die Bedingung welche zur Beendigung eines Spiel erfüllt sein müssen.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spielregel {

    public boolean regelErfuellt;


}
