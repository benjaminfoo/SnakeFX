package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.ChatMessage;
import de.ostfalia.snakecore.ws.model.GameInputMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

public class StompClient extends StompSessionHandlerAdapter implements MessageRecievedCallback {

    // ws://localhost:8080/snakeserver/
    private String connectionUrl = "ws://localhost:8080/snakeserver";

    private StompSessionHandler sessionHandler;

    private Logger logger = LogManager.getLogger(StompSessionHandler.class);

    private StompSession session;
    private MessageRecievedCallback messageRecievedCallback;


    public void connect(String url) {
        this.connectionUrl = url;

        // initialize the clients
        WebSocketClient wsClient = new StandardWebSocketClient();
        WebSocketStompClient wsStompClient = new WebSocketStompClient(wsClient);

        // register a jackson 2 message converter
        wsStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // instantiate the session handler in order to communicate over STOMP
        sessionHandler = this;

        // CONNECT to the Stomp server using the url and the sessionHandler
        wsStompClient.connect(connectionUrl, sessionHandler);

        // register a listener for incoming messages
        setMessageRecievedCallback(this);
    }


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;

        logger.info("New session established : " + session.getSessionId());

        session.subscribe("/topic/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {

                System.out.println("Received message: " + headers.getMessageId() + " | " + headers.getDestination());

                ChatMessage msg = (ChatMessage) payload;
                if (msg != null) {
                    System.out.println("Received ChatMessage: " + msg.getText() + " from : " + msg.getFrom());
                    messageRecievedCallback.chatMessageRecieved(msg);
                }

            }
        });
        logger.info("Subscribed to " + "/topic/messages");

        session.subscribe("/topic/games", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return LobbyMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + headers.getMessageId() + " | " + headers.getDestination());

                LobbyMessage msg = (LobbyMessage) payload;
                if (msg != null) {
                    System.out.println("Received LobbyMessage: " + msg.runningGames.toString());
                    messageRecievedCallback.onRecievedLobbyMessage(msg);
                }
            }
        });
        logger.info("Subscribed to " + "/topic/games");



        /*
        session.send(ProjectEndpoints.STOMP_APP_PREFIX + ProjectEndpoints.STOMP_MESSAGE_MAPPING_CHAT, getSampleMessage());
        logger.info("Message sent to websocket server");
        */
    }

    public void sendChatMessage(String user, String message) {
        System.out.println("Sending message to /app/chat ... Message: " + message);
        session.send("/app/chat", new ChatMessage(user, message));
    }

    public void sendLobbyMessage() {
        System.out.println("Sending lobby message");
        session.send("/app/games", new LobbyMessage());
    }

    /**
     * Sends a message to a game topic with gameId - from the player with the userName and its current input
     *
     * @param gameId
     * @param userName
     * @param input
     */
    public void subsribeToGameTopic(String gameId, String userName, String input) {
        session.send("/app/games/" + gameId, new GameInputMessage(userName, input));
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

    }

    public void setMessageRecievedCallback(MessageRecievedCallback messageRecievedCallback) {
        this.messageRecievedCallback = messageRecievedCallback;
    }

    public void setRecievedCallback(MessageRecievedCallback recievedCallback) {
        this.messageRecievedCallback = recievedCallback;
    }

    @Override
    public void chatMessageRecieved(ChatMessage msg) {
        if (messageRecievedCallback != null) {
            messageRecievedCallback.chatMessageRecieved(msg);
        }
    }

    @Override
    public void onRecievedLobbyMessage(LobbyMessage msg) {
        if (messageRecievedCallback != null) {
            messageRecievedCallback.onRecievedLobbyMessage(msg);
        }
    }

}