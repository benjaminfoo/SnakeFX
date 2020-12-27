package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.ChatMessage;
import de.ostfalia.snakecore.ws.model.GameInputMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import de.ostfalia.snakecore.ws.model.PlayerMessage;

/**
 * @author Benjamin Wulfert
 *
 * A set of callbacks which are triggered when the backend communicates with the frontend.
 */
public interface StompMessageListener {

    // callback for indicating a new chat message
    public void onChatMessageReceived(ChatMessage msg);

    // callback for indicating a newly created game within the lobby
    public void onLobbyMessageReceived(LobbyMessage msg);

    // callback for indicating a player has joined the lobby
    public void onPlayerMessageReceived(PlayerMessage msg);

    // callback for indicating recieved player input from the backend
    public void onGameInputMessageReceived(GameInputMessage msg);

}