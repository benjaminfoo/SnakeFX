package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.LobbyMessage;

public interface RecievedLobbyMessageCallback {
    public void recievedLobbyMessage(LobbyMessage lobbyMessage);
}