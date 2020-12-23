package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.ws.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

import static de.ostfalia.snakecore.ProjectEndpoints.STOMP_DESTINATION_CHAT;

public class StompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(StompSessionHandler.class);
    private StompSession session;
    private MessageRecievedCallback messageRecievedCallback;

    public interface MessageRecievedCallback {
        public void messageRecieved(Message msg);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        logger.info("New session established : " + session.getSessionId());

        session.subscribe(ProjectEndpoints.STOMP_TOPIC_MESSAGES, this);
        logger.info("Subscribed to " + ProjectEndpoints.STOMP_TOPIC_MESSAGES);

        /*
        session.send(ProjectEndpoints.STOMP_APP_PREFIX + ProjectEndpoints.STOMP_MESSAGE_MAPPING_CHAT, getSampleMessage());
        logger.info("Message sent to websocket server");
        */
    }

    public void sendMessage(String user, String message){
        session.send(STOMP_DESTINATION_CHAT, new Message(user, message));
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());

        if(messageRecievedCallback != null){
            messageRecievedCallback.messageRecieved(msg);
        }
    }

    public void setMessageRecievedCallback(MessageRecievedCallback messageRecievedCallback) {
        this.messageRecievedCallback = messageRecievedCallback;
    }
}