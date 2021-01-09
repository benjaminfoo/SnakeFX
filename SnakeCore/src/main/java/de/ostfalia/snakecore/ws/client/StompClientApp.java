package de.ostfalia.snakecore.ws.client;

import java.util.Scanner;

/**
 * @author Benjamin Wulfert
 *
 * A simple text based stomp-client to exchange stomp messages.
 * This class is used for testing / debugging the communication between clients and the backend.
 */
public class StompClientApp {


    // This is just for testing the stompClient solely
    public static void main(String[] args) {
        StompClient s = new StompClient();
        s.connect("ws://localhost:8080/snakeserver", () -> {
            System.out.println("Connected");
        });

        // eval loop for messaging with the stomp server
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        do {
            userInput = scanner.nextLine();
            s.sendChatMessage("Benni", userInput);
        } while (!userInput.equals("!quit") || !userInput.equals("!q"));
    }

}
