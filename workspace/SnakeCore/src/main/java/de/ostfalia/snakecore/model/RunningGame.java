package de.ostfalia.snakecore.model;

import java.util.List;

public class RunningGame {

    public String stompPath;

    public Spieler admin;

    public List<Spieler> activeClients;

    public SpielDefinition spielDefinition;

    public RunningGame() {
    }

    public RunningGame(String stompPath, Spieler admin, List<Spieler> activeClients, SpielDefinition spielDefinition) {
        this.stompPath = stompPath;
        this.admin = admin;
        this.activeClients = activeClients;
        this.spielDefinition = spielDefinition;
    }

    public String getStompPath() {
        return stompPath;
    }

    public void setStompPath(String stompPath) {
        this.stompPath = stompPath;
    }

    public Spieler getAdmin() {
        return admin;
    }

    public void setAdmin(Spieler admin) {
        this.admin = admin;
    }

    public List<Spieler> getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(List<Spieler> activeClients) {
        this.activeClients = activeClients;
    }

    public SpielDefinition getSpielDefinition() {
        return spielDefinition;
    }

    public void setSpielDefinition(SpielDefinition spielDefinition) {
        this.spielDefinition = spielDefinition;
    }
}
