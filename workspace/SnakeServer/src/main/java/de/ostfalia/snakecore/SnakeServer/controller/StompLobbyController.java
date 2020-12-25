package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.ws.model.GameInputMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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
    public LobbyMessage broadcastGames(Message<LobbyMessage> message) throws Exception {
        LobbyMessage lobbyMessage = message.getPayload();
        lobbyMessage.runningGames = lobbyController.getRunningGames();
        System.out.println("Transmit all lobby games to client");
        return lobbyMessage;
    }

    @MessageMapping("/games/{gameId}")
    @SendTo("/topic/games/{gameId}")
    public GameInputMessage broadcastPlayerInputToClients(@DestinationVariable String gameId, GameInputMessage message) {
        System.out.println("Recieved player input for " + gameId);
        System.out.println("Player input: " + message);
        return message;
    }

}
