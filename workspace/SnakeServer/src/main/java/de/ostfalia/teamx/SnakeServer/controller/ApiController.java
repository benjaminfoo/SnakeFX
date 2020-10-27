package de.ostfalia.teamx.SnakeServer.controller;

import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.teamx.SnakeServer.model.Spieler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
 
    @ResponseBody
    @RequestMapping("/api/spieler")
    public String index() {
        Iterable<Spieler> all = spielerRepository.findAll();
 
        StringBuilder sb = new StringBuilder();
 
        all.forEach(p -> sb.append(p.toString() + "<br>"));
 
        return sb.toString();
    }

}
