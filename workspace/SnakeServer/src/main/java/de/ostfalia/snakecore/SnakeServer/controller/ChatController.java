package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.ws.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

import static de.ostfalia.snakecore.ApplicationConstants.BOT_USER_NAME;
import static de.ostfalia.snakecore.ProjectEndpoints.STOMP_TOPIC_MESSAGES;

@Controller
public class ChatController {

    private SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }


    @MessageMapping(ProjectEndpoints.STOMP_MESSAGE_MAPPING_CHAT)
    @SendTo(STOMP_TOPIC_MESSAGES)
    public Message send(final Message message) {
        preProcess(message);
        return message;
    }

    private void preProcess(Message message) {

        if(message.getText().contains("time?")){
            template.convertAndSend(STOMP_TOPIC_MESSAGES, new Message(BOT_USER_NAME, "Die aktuelle Uhrzeit auf diesem Server lautet: " + new Date()));
        }

        if(message.getText().toLowerCase().contains("hallo bot".toLowerCase())){
            template.convertAndSend(STOMP_TOPIC_MESSAGES, new Message(BOT_USER_NAME, "Hallo " + message.getFrom() + ", wie gehts?"));
        }

        if(message.getText().toLowerCase().contains("bot".toLowerCase())){
            template.convertAndSend(STOMP_TOPIC_MESSAGES, new Message(BOT_USER_NAME, message.getFrom() + " - ich habe meinen Namen gehört?"));
        }

    }

}