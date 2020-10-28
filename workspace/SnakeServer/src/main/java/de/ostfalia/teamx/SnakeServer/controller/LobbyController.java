package de.ostfalia.teamx.SnakeServer.controller;

import de.ostfalia.teamx.SnakeServer.model.Lobby;
import de.ostfalia.teamx.SnakeServer.model.Spielrunde;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class LobbyController {

    public Lobby lobby = new Lobby();

    public void addNewGame(Spielrunde newRound){
        lobby.aktiveSpiele.add(newRound);
    }

}
