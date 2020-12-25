package de.ostfalia.snakecore.model;

import java.util.List;

public class RunningGame {

    public Spieler admin;

    public List<Spieler> activeClients;

    public SpielDefinition spielDefinition;

    public RunningGame() {
    }

    public RunningGame(Spieler admin, List<Spieler> activeClients, SpielDefinition spielDefinition) {
        this.admin = admin;
        this.activeClients = activeClients;
        this.spielDefinition = spielDefinition;
    }

}
