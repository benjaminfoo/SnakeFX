package de.ostfalia.snakecore.ws.client;

import de.ostfalia.snakecore.ws.model.ChatMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;

public interface MessageRecievedCallback {

    public void chatMessageRecieved(ChatMessage msg);

    public void onRecievedLobbyMessage(LobbyMessage msg);
}