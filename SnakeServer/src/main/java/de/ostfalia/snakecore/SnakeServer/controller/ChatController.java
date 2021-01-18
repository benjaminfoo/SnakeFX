package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.ws.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.lang.management.ManagementFactory;
import java.util.Date;

import static de.ostfalia.snakecore.ApplicationConstants.BOT_USER_NAME;

/**
 * @author Benjamin Wulfert
 *
 * The ChatController handles the interaction between the connected client-frontends and this backend.
 */
@Controller
public class ChatController {

    private SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * A client needs to send a message to host/app/chat
     * These messages are broadcastet to host/topic/chat/
     */

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage send(Message<ChatMessage> message) {
        ChatMessage chatMessage = message.getPayload();
        preProcess(chatMessage);
        System.out.println("Recieved " + chatMessage);
        return chatMessage;
    }

    private void preProcess(ChatMessage chatMessage) {

        if (chatMessage.getText().contains("time?")) {
            template.convertAndSend("/topic/messages", new ChatMessage(BOT_USER_NAME, "Die aktuelle Uhrzeit auf diesem Server lautet: " + new Date()));
        }

        if (chatMessage.getText().contains("ping")) {
            template.convertAndSend("/topic/messages", new ChatMessage(BOT_USER_NAME, "Pong"));
        }

        if (chatMessage.getText().toLowerCase().contains("hallo bot".toLowerCase())) {
            template.convertAndSend("/topic/messages", new ChatMessage(BOT_USER_NAME, "Hallo " + chatMessage.getFrom() + ", wie gehts?"));
        }

        if (chatMessage.getText().toLowerCase().contains("bot".toLowerCase())) {
            template.convertAndSend("/topic/messages", new ChatMessage(BOT_USER_NAME, chatMessage.getFrom() + " - ich habe meinen Namen gehört?"));
        }

    }

}