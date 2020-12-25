package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.ws.model.LobbyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
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
        System.out.println("Someone wants to retrieve all games, soooo");
        return lobbyMessage;
    }

}
