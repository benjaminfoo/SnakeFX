package de.ostfalia.snakecore.SnakeServer.controller;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielDefinitionRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandRepository;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.Spielstand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SpielDefinitionRepository spielDefinitionRepository;

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
    public ResponseEntity<Spieler> login (@RequestBody Spieler spieler) {
        // spieler.pass(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        // lobbyController.lobby.aktiveSpieler.add(spieler);

        String loginName = spieler.getName().trim();
        String loginPass = spieler.getPass();

        Spieler resultFromDB = null;

        boolean loginValid = false;

        resultFromDB = spielerRepository.findByName(loginName);

        if(resultFromDB == null){
            // TODO: return some error
        } else {

            if(loginPass.equals(resultFromDB.getPass())){
                loginValid = true;
            }

        }

        if(loginValid){
            return new ResponseEntity<Spieler>(resultFromDB, HttpStatus.OK);
        } else {
            System.out.println("INVALID LOGIN DETECTED FROM " + loginName + " with Pass: " + loginPass);
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = {API_PATH + API_ENDPOINT_LOBBY})
    public ResponseEntity<List<SpielDefinition>> getLobby() {
        try {
            return new ResponseEntity<List<SpielDefinition>>(spielDefinitionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = {API_PATH + API_ENDPOINT_LOBBY})
    public void postNewGame(@RequestBody SpielDefinition neuesSpiel) {

        // TODO: maybe check if there are inconsistencies or some other potential problems
        spielDefinitionRepository.save(neuesSpiel);

    }


}
