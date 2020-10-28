package de.ostfalia.teamx.SnakeServer.controller;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Benjamin Wulfert
 * The model for a player.
 */
@RestController
public class ApiController {
 
    @Autowired
    private SpielerRepository spielerRepository;

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


}
