package de.ostfalia.snakecore.SnakeServer.runner;

import de.ostfalia.snakecore.SnakeServer.persistance.*;
import de.ostfalia.snakecore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Benjamin Wulfert
 * The DataInitRunner gets called when the application gets started.
 * The run() method gets called when the DataInitRunner has been started
 *
 * This is used to pre populate the database with test data.
 */
@Component
public class DataInitRunner implements ApplicationRunner {

    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private SpielstandRepository spielstandRepository;

    @Autowired
    private SpielstandErgebnisRepository spielstandErgebnisRepository;

    @Autowired
    private SpielDefinitionRepository spielDefinitionRepository;

    @Autowired
    private SpielregelRepository spielregelRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        prepopulateGameHistoryData();
        prepopulateGameDefinitions();

    }

    /**
     * Generiert Spieldefinitionen welche in der Lobby sichtbar sind.
     */
    private void prepopulateGameDefinitions() {

        Spielregel spielregel = new Spielregel("Highscore: 100", Spielregel.Type.HIGHSCORE_100);
        Spielregel spielregel2 = new Spielregel("Highscore: 50", Spielregel.Type.HIGHSCORE_50);

        spielregelRepository.save(spielregel);
        spielregelRepository.save(spielregel2);

        SpielDefinition spielDefinition = new SpielDefinition(
                "Spiel 1",
                2,
                20,
                32,
                32,
                spielregel
        );

        SpielDefinition spielDefinition2 = new SpielDefinition(
                "Spiel 2",
                4,
                10,
                64,
                64,
                spielregel2
        );

        spielDefinitionRepository.save(spielDefinition);
        spielDefinitionRepository.save(spielDefinition2);
    }

    /**
     * Generiert Spieler, Spielstände und Spielergebnisse - die Ergebnisse können in vers. Ansichten des Frontends
     * betrachtet werden (z.b. in der Historie-View).
     */
    private void prepopulateGameHistoryData() {

        // Szenario 1 - Zwei Spieler
        Spieler testSpieler = new Spieler(1L, "p1", "p1");
        Spieler testSpieler2 = new Spieler(2L, "p2", "p2");

        spielerRepository.save(testSpieler);
        spielerRepository.save(testSpieler2);

        Spielstand spielstand1 = new Spielstand();
        SpielstandErgebnis ergebnis1 = new SpielstandErgebnis(1L, 100, testSpieler);
        SpielstandErgebnis ergebnis2 = new SpielstandErgebnis(2L, 58, testSpieler2);

        spielstand1.id = 1L;
        spielstand1.date = System.currentTimeMillis() - 13371337 * 20;
        ergebnis1.spielstand = spielstand1;
        ergebnis2.spielstand = spielstand1;

        spielstandRepository.save(spielstand1);
        spielstandErgebnisRepository.save(ergebnis1);
        spielstandErgebnisRepository.save(ergebnis2);

        // Szenario 2 - Drei Spieler
        Spieler testSpieler3 = new Spieler(3L, "p3", "p3");
        spielerRepository.save(testSpieler3);

        Spielstand spielstand2 = new Spielstand();
        spielstand2.date = System.currentTimeMillis() + 13371337 * 20;
        SpielstandErgebnis ergebnis21 = new SpielstandErgebnis(3L, 13, testSpieler);
        SpielstandErgebnis ergebnis22 = new SpielstandErgebnis(4L, 100, testSpieler2);
        SpielstandErgebnis ergebnis23 = new SpielstandErgebnis(5L, 70, testSpieler3);

        spielstand2.id = 2L;
        ergebnis21.spielstand = spielstand2;
        ergebnis22.spielstand = spielstand2;
        ergebnis23.spielstand = spielstand2;

        spielstandRepository.save(spielstand2);
        spielstandErgebnisRepository.save(ergebnis21);
        spielstandErgebnisRepository.save(ergebnis22);
        spielstandErgebnisRepository.save(ergebnis23);
    }

}
