package de.ostfalia.snakecore.model;

import java.util.List;

public class Spielrunde {

    public String name;

    // TODO: implement missing

    public List<Spieler> spieler;

    public SpielDefinition spielDefinition;

    public Spielrunde() {
    }

    public Spielrunde(String name, List<Spieler> spieler, SpielDefinition spielDefinition) {
        this.name = name;
        this.spieler = spieler;
        this.spielDefinition = spielDefinition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Spieler> getSpieler() {
        return spieler;
    }

    public void setSpieler(List<Spieler> spieler) {
        this.spieler = spieler;
    }

    public SpielDefinition getSpielDefinition() {
        return spielDefinition;
    }

    public void setSpielDefinition(SpielDefinition spielDefinition) {
        this.spielDefinition = spielDefinition;
    }
}
