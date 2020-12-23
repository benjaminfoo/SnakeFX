package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandRepository;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.Spielrunde;
import de.ostfalia.snakecore.model.Spielstand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static de.ostfalia.snakecore.ProjectEndpoints.*;

/**
 * @author Benjamin Wulfert
 * The ApiController handles the interaction between the web and the database, and other various mechanics from the system.
 */
@RestController
public class ApiController {
 
    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private SpielstandRepository spielstandRepository;

    // TODO: Implement password encryption with bCrypt
    // @Autowired
    // BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = {API_PATH + API_ENDPOINT_SPIELER})
    public ResponseEntity<Iterable<Spieler>> getSpieler() {
        try {
            return new ResponseEntity<>(spielerRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = {API_PATH + API_ENDPOINT_HISTORIE})
    public ResponseEntity<List<Spielstand>> getHistorie() {
        try {
            return new ResponseEntity<List<Spielstand>>(spielstandRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @RequestMapping(path = {API_PATH + API_ENDPOINT_REGISTER})
    public void register(@RequestBody Spieler spieler) {
        // spieler.pass(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        spielerRepository.save(spieler);
    }


    @PostMapping(path = {API_PATH + API_ENDPOINT_LOGIN})
    public void login(@RequestBody Spieler spieler) {
        // spieler.pass(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        // lobbyController.lobby.aktiveSpieler.add(spieler);
    }

    @GetMapping(path = {API_PATH + API_ENDPOINT_LOBBY})
    public ResponseEntity<List<SpielDefinition>> getLobby() {
        try {
            // return new ResponseEntity<List<SpielDefinition>>(lobbyController.getSpiele(), HttpStatus.OK);
            // TODO; this list is empty!!!!
            return new ResponseEntity<List<SpielDefinition>>(Collections.emptyList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = {API_PATH + API_ENDPOINT_LOBBY})
    public void postNewGame(@RequestBody SpielDefinition neuesSpiel) {

        // erzeuge eine neue Spielrunde anhand der Spieldefinition
        Spielrunde spielrunde = new Spielrunde();
        spielrunde.name = neuesSpiel.getNameOfTheGame();

        // füge die neue Spielrunde der Lobby hinzu
        //TODO:  lobbyController.lobby.aktiveSpiele.add(spielrunde);

        System.out.println("Got new game: " + spielrunde);
    }


}
