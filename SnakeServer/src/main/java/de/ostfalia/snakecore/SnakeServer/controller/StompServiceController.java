package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandRepository;
import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.Spielstand;
import de.ostfalia.snakecore.model.SpielstandErgebnis;
import de.ostfalia.snakecore.pattern.MapEntity;
import de.ostfalia.snakecore.model.game.Snake;
import de.ostfalia.snakecore.model.math.Vector2;
import de.ostfalia.snakecore.util.RNG;
import de.ostfalia.snakecore.ws.model.GameSessionMessage;
import de.ostfalia.snakecore.ws.model.LobbyMessage;
import de.ostfalia.snakecore.ws.model.PlayerJoinsGameMessage;
import de.ostfalia.snakecore.ws.model.PlayerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * @author Benjamin Wulfert
 *
 * The StompServiceController handles all communication between connected clients and this backend via WebSockets / STOMP-Protocoll.
 */
@Controller
public class StompServiceController {


    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    public LobbyController lobbyController;

    @Autowired
    private SpielstandRepository spielstandRepository;

    @Autowired
    private SpielerRepository spielerRepository;




    @MessageMapping("/games") // "/app/games/
    @SendTo("/topic/games")
    public LobbyMessage broadcastGames(LobbyMessage lobbyMessage) {

        if (lobbyMessage.logout && lobbyMessage.logoutSpieler != null) {
            lobbyController.getCurrentPlayers().remove(lobbyMessage.logoutSpieler);
            System.out.println("Removing player: " + lobbyMessage.logoutSpieler + " from the active players of the lobby ...");

            lobbyMessage.activeClients = lobbyController.getCurrentPlayers();
        }

        if (lobbyMessage.spielDefinition != null) {
            System.out.println("New game definition recieved: " + lobbyMessage.spielDefinition.getNameOfTheGame());
            System.out.println("Adding it to the lobby ... ");

            // create a new runningGame based on the SpielDefinition
            RunningGame newRunningGame = new RunningGame(
                    "/topic/games/1",
                    lobbyMessage.admin,
                    new LinkedList<>(Arrays.asList(lobbyMessage.admin)),
                    lobbyMessage.spielDefinition
            );

            // add it to the lobby
            lobbyController.add(newRunningGame);
        }

        // re-reference the current running games from the lobby to this message
        lobbyMessage.runningGames = lobbyController.getRunningGames();

        // broadcast it to every connected client
        return lobbyMessage;
    }

    @MessageMapping("/players") // "/app/players/
    @SendTo("/topic/players")
    public PlayerMessage broadCastPlayers(PlayerMessage playerMessage) {

        lobbyController.getCurrentPlayers().add(playerMessage.newPlayer);

        playerMessage.playersInLobby = lobbyController.getCurrentPlayers();

        return playerMessage;
    }

    @Scheduled(fixedRate = 5000)
    public void broadcastGameFrame() {
        this.template.convertAndSend("/topic/games/1", "Hello");
    }


    /**
     * This method is used to communicate running-game related changes to subscribed clients.
     *
     * @param gameId
     * @param m
     * @return
     */
    @MessageMapping("/games/{gameId}")
    @SendTo("/topic/games/{gameId}")
    public GameSessionMessage broadcastPlayerInputToClients(@DestinationVariable String gameId, GameSessionMessage m) {

        System.out.println("StompLobbyController - Received player input for game: " + gameId + " - " + m);

        // the game is starting
        if (m.getGameState() == GameSessionMessage.GameState.STARTING && m.getInput() == null) {

            // generation for the first food / powerup
            // the first food / powerup always spawns on the center of the game
            int newX = m.getRunningGame().getSpielDefinition().getMapWidth();
            int newY = m.getRunningGame().getSpielDefinition().getMapHeight();

            Set<MapEntity> result = new HashSet<>();
            for (int i = 0; i < m.getRunningGame().getSpielDefinition().getMaxNumberOfPowerUps(); i++) {
                Vector2 entityPosition = generateEntityPosition(newX, newY, Collections.emptyList());

                MapEntity newMapEntity = new MapEntity(null, entityPosition);

                // we generate zeros from 1 - N because
                newMapEntity.kindOf = RNG.getInstance().generate(1, MapEntity.MAP_ENTITY_KINDS.length);

                newMapEntity.drawableId = RNG.getInstance().generate(0, m.amountOfFoodDrawables);
                result.add(newMapEntity);

            }
            m.setFoods(result);

        }

        // the game is running
        if (m.getGameState() == GameSessionMessage.GameState.RUNNING && m.getInput() == null) {

            /*

            // generation for every other food / powerup
            if (m.foodPosition != null && m.getRunningGame() != null && m.foodPosition.size() != m.getRunningGame().getSpielDefinition().getMaxNumberOfPowerUps()) {
                List<Vector2> result = new LinkedList<>();
                int generationAmount = m.getRunningGame().getSpielDefinition().getMaxNumberOfPowerUps() - m.foodPosition.size();
                for (int i = 0; i < generationAmount; i++) {
                    Vector2 e = generateFoodPosition(m.config.columns, m.config.rows, m.snakeList);
                    System.out.println("Generated new food position at: " + e.toString());
                    result.add(e);
                }
                m.setFoodPosition(result);
            }

            */
        }

        // if there is an incoming message ...
        // ... with the state of FINISHING
        if (m.getGameState() == GameSessionMessage.GameState.FINISHING) {

            // ... and there is a winner and a loser result defined
            if (m.winnerResult != null && m.loserResults != null) {

                // create a new saveState
                Spielstand spielstand = new Spielstand();
                spielstand.ergebnisse.add(m.winnerResult);
                spielstand.ergebnisse.addAll(m.loserResults);

                // write that mofo to the database
                spielstandRepository.save(spielstand);

            }

        }


        // if there is an incoming message ...
        // ... with the state of FINISHING
        if (m.getGameState() == GameSessionMessage.GameState.FINISHING) {

            // ... and there is a winner and a loser result defined
            if (m.loserResults != null && m.winnerResult == null) {

                // create a new saveState
                Spielstand spielstand = new Spielstand();

                spielstand.date = System.currentTimeMillis();

                for (SpielstandErgebnis loserResult : m.loserResults) {
                    spielerRepository.save(loserResult.spieler);
                    loserResult.spielstand = spielstand;
                }

                spielstand.ergebnisse.addAll(m.loserResults);

                // write that mofo to the database
                spielstandRepository.save(spielstand);

            }

        }


        /*
        // we'll use the absence of a player to get the initial food position
        // this is needed because we need a valid food position in the game initially
        if(m.foodConsumed && m.getPlayer() == null){
            m.newFoodPosition = generateFoodPosition(m.config, m.snakeList);
        }

        // ...
        if(m.foodConsumed && m.getPlayer() != null){
            // we have to regenerate a new food / powerup position
            m.newFoodPosition = generateFoodPosition(m.config, m.snakeList);
        }
        */

        // returning the message will automatically broadcast it to every other client
        return m;
    }

    @MessageMapping("/games/{gameId}/{playerId}")
    @SendTo("/topic/games/{gameId}/{playerId}")
    public PlayerJoinsGameMessage broadcastPlayerJoinedGameToClients(@DestinationVariable String gameId, @DestinationVariable String playerId, PlayerJoinsGameMessage message) {
        System.out.println("");
        System.out.println("Player " + playerId + " wants to join the game " + gameId);
        System.out.println("");

        // make the player join the lobby
        RunningGame destination = null;

        for (RunningGame runningGame : lobbyController.getRunningGames()) {
            if (runningGame.getStompPath().equals(message.gameToJoin.stompPath)) {
                destination = runningGame;
            }
        }

        List<Spieler> newActivePlayers = new LinkedList<>(destination.activeClients);
        newActivePlayers.add(message.spieler);

        destination.activeClients = newActivePlayers;

        message.allGames = lobbyController.getRunningGames();

        return message;
    }


    // UTIL

    /**
     * Generates a valid food position.
     * Valid means the food isnt spawned on the same position like a snakes bodypart, another food or a powerup
     * @param snakeList
     * @return
     */
    private Vector2 generateEntityPosition(int x, int y, List<Snake> snakeList) {

        boolean invalidFoodPosition = true;

        int newX;
        int newY;

        do {

            // calculate new positions
            newX = RNG.getInstance().generate(0, x);
            newY = RNG.getInstance().generate(0, y);

            for (Snake snake : snakeList) {
                if (snake.head.x != newX && snake.head.y == newY) {
                    invalidFoodPosition = false;
                }
                for (Vector2 bodyPart : snake.body) {
                    if (bodyPart.x != newX && bodyPart.y != newY) {
                        invalidFoodPosition = false;
                    }
                }
            }

            if(snakeList.isEmpty()){ invalidFoodPosition = false; }

        } while (invalidFoodPosition);

        return new Vector2(newX, newY);
    }

}
