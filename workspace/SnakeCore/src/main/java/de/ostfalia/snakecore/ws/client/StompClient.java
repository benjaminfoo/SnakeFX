package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

import static de.ostfalia.snakecore.ProjectEndpoints.URL_STOMP_BASE;

public class StompClient implements StompSessionHandler.MessageRecievedCallback {

    // ws://localhost:8080/stomp/chat
    private static String CONNECTION_URL = URL_STOMP_BASE;

    private StompSessionHandler sessionHandler;

    private StompSessionHandler.MessageRecievedCallback recievedCallback;

    // This is just for testing the stompClient solely
    public static void main(String[] args) {
        StompClient s = new StompClient();
        s.connect();

        // eval loop for messaging with the stomp server
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        do {
            userInput = scanner.nextLine();
            s.sendMessage("Benni", userInput);
        } while (!userInput.equals("!quit") || !userInput.equals("!q"));
    }

    public void connect(){
        connect(CONNECTION_URL);
    }

    public void connect(String url){
        // initialize the clients
        WebSocketClient wsClient = new StandardWebSocketClient();
        WebSocketStompClient wsStompClient = new WebSocketStompClient(wsClient);

        // register the message converter
        wsStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // instantiate the session handler in order to communicate over STOMP
        sessionHandler = new StompSessionHandler();

        // CONNECT to the Stomp server using the url and the sessionHandler
        wsStompClient.connect(CONNECTION_URL, sessionHandler);

        // register a listener for incoming messages
        sessionHandler.setMessageRecievedCallback(this);
    }

    public void sendMessage(String userName, String message){
        ((StompSessionHandler) sessionHandler).sendMessage(userName, message);
    }

    public boolean isConnected(){
        boolean result = false;

        result = sessionHandler == null; // TODO this isnt enough

        return result;
    }

    @Override
    public void messageRecieved(Message msg) {
        if(recievedCallback != null){
            recievedCallback.messageRecieved(msg);
        }
    }

    public void setRecievedCallback(StompSessionHandler.MessageRecievedCallback recievedCallback) {
        this.recievedCallback = recievedCallback;
    }
}