package de.ostfalia.teamx.SnakeServer.controller;

import de.ostfalia.teamx.SnakeServer.model.Lobby;
import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.model.Spielhistorie;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Benjamin Wulfert
 * The model for a player.
 */
@RestController
public class ApiController {
 
    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private LobbyController lobbyController;

    @Autowired
    private HistorienController historienController;

    // TODO: Implement password encryption with bCrypt
    // @Autowired
    // BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping("api/spieler/{name}")
    public ResponseEntity getSpieler(@PathVariable("name") String name) {
        Optional<Spieler> product = spielerRepository.findByName(name);
        if (product.isPresent()) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = {"api/spieler", "api/spieler/"})
    public ResponseEntity<Iterable<Spieler>> getSpieler() {
        try {
            return new ResponseEntity<>(spielerRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = {"api/lobby", "api/lobby/"})
    public ResponseEntity<Lobby> getLobby() {
        try {
            return new ResponseEntity<Lobby>(lobbyController.lobby, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = {"api/historie", "api/historie/"})
    public ResponseEntity<Spielhistorie> getHistorie() {
        try {
            return new ResponseEntity<Spielhistorie>(historienController.spielhistorie, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @RequestMapping(path = {"api/register", "api/register/"})
    public void register(@RequestBody Spieler spieler) {
        // spieler.pass(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        spielerRepository.save(spieler);
    }


}
