package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.ws.model.GameInputMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import de.ostfalia.snakecore.ws.model.PlayerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Collections;

@Controller
public class StompLobbyController {

    private SimpMessagingTemplate template;

    @Autowired
    public StompLobbyController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    public LobbyController lobbyController;


    @MessageMapping("/games") // "/app/games/
    @SendTo("/topic/games")
    public LobbyMessage broadcastGames(LobbyMessage lobbyMessage)  {

        if(lobbyMessage.spielDefinition != null){
            System.out.println();
            System.out.println("New game definition recieved: " + lobbyMessage.spielDefinition.getNameOfTheGame());
            System.out.println("Adding it to the lobby ... " );
            System.out.println();

            // create a new runningGame based on the SpielDefinition
            RunningGame newRunningGame = new RunningGame(
                    "/app/games/1",
                    lobbyMessage.admin,
                    Collections.emptyList(),
                    lobbyMessage.spielDefinition
            );

            // add it to the lobby
            lobbyController.add(newRunningGame);
        }

        // re-reference the current running games from the lobby to this message
        lobbyMessage.runningGames = lobbyController.getRunningGames();

        // broadcast it to every connected client
        return lobbyMessage;
    }


    @MessageMapping("/players") // "/app/players/
    @SendTo("/topic/players")
    public PlayerMessage broadCastPlayers(PlayerMessage playerMessage) {

        lobbyController.getCurrentPlayers().add(playerMessage.newPlayer);

        playerMessage.playersInLobby = lobbyController.getCurrentPlayers();

        return playerMessage;
    }


    @MessageMapping("/games/{gameId}")
    @SendTo("/topic/games/{gameId}")
    public GameInputMessage broadcastPlayerInputToClients(@DestinationVariable String gameId, GameInputMessage message) {
        System.out.println("Recieved player input for " + gameId);
        System.out.println("Player input: " + message);
        return message;
    }

}
