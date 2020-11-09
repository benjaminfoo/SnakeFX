package de.ostfalia.teamx.SnakeServer.controller;

import de.ostfalia.teamx.SnakeServer.model.Lobby;
import de.ostfalia.teamx.SnakeServer.model.Spielrunde;
import de.ostfalia.teamx.shared.SpielDefinition;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class LobbyController {

    public Lobby lobby = new Lobby();

    public void addNewGame(Spielrunde newRound){
        lobby.aktiveSpiele.add(newRound);
    }

    public List<SpielDefinition> getSpiele(){
        List<SpielDefinition> spielDefinition = new LinkedList<>();

        for (Spielrunde spielrunde : lobby.aktiveSpiele) {
            SpielDefinition s = new SpielDefinition();
            s.setNameOfTheGame(spielrunde.getName());

            // TODO: finish mapping
            spielDefinition.add(s);
        }

        return spielDefinition;
    }

}
