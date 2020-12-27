package de.ostfalia.snakecore.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunningGame that = (RunningGame) o;
        return Objects.equals(stompPath, that.stompPath) && Objects.equals(admin, that.admin) && Objects.equals(activeClients, that.activeClients) && Objects.equals(spielDefinition, that.spielDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stompPath, admin, activeClients, spielDefinition);
    }

    @Override
    public String toString() {
        return "RunningGame{" +
                "stompPath='" + stompPath + '\'' +
                ", admin=" + admin +
                ", activeClients=" + activeClients +
                ", spielDefinition=" + spielDefinition +
                '}';
    }
}
