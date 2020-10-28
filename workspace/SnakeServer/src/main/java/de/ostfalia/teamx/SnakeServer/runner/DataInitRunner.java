package de.ostfalia.teamx.SnakeServer.runner;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.model.Spielstand;
import de.ostfalia.teamx.SnakeServer.model.SpielstandErgebnis;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandErgebnisRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Benjamin Wulfert
 * The DataInitRunner gets called when the application gets started.
 * The run() method gets called when the DataInitRunner has been started
 */
@Component
public class DataInitRunner implements ApplicationRunner {

    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private SpielstandRepository spielstandRepository;

    @Autowired
    private SpielstandErgebnisRepository spielstandErgebnisRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {


        // Definition Test-Spieler
        Spieler testSpieler = new Spieler(-1, "Test-Spieler", "Test-Passwort");
        Spieler testSpieler2 = new Spieler(-1, "Test-Spieler-2", "Test-Passwort-2");

        // Def. eines Ergebnisses
        SpielstandErgebnis testErgebnis = new SpielstandErgebnis(-1, testSpieler, 37);

        // Speichern des Ergebnisses
        spielstandErgebnisRepository.save(testErgebnis);

        // Definition eines Spielstands
        Spielstand spielstand = new Spielstand();
        spielstand.getErgebnisse().add(testErgebnis);

        // Speichern des Spielstands
        // spielstandRepository.save(spielstand);

        System.out.println("Initial database has been initialized");
    }
     
}
