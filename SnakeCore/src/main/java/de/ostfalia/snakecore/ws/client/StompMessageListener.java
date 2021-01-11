package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.*;

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
    public void onGameSessionMessageReceived(GameSessionMessage msg);

    // callback for indicating a player has joined a game in the lobby
    public void onPlayerJoinedGameMessageReceived(PlayerJoinsGameMessage msg);

}