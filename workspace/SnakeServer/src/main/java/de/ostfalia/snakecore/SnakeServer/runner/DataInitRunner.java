package de.ostfalia.snakecore.SnakeServer.runner;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandErgebnisRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandRepository;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.Spielstand;
import de.ostfalia.snakecore.model.SpielstandErgebnis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

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

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        prepopulateTestData();

        /*
        spielstand1.setErgebnisse(new HashSet<>(Arrays.asList(ergebnis1, ergebnis2)));


        spielstandRepository.save(spielstand1);
        */


        /*
        // Definition Test-Spieler


        SpielstandErgebnis ergebnisSpieler1 = new SpielstandErgebnis(testSpieler, 42);
        SpielstandErgebnis ergebnisSpieler2 = new SpielstandErgebnis(testSpieler2, 100);

        // spielstandErgebnisRepository.save(ergebnisSpieler1);
        // spielstandErgebnisRepository.save(ergebnisSpieler2);

        Set<SpielstandErgebnis> spielstandErgebnisSet = new HashSet<>();

        spielstandErgebnisSet.add(ergebnisSpieler1);
        spielstandErgebnisSet.add(ergebnisSpieler2);

        Spielstand spielstand = new Spielstand();

        spielstand.getErgebnisse().addAll(spielstandErgebnisSet);

        spielstandRepository.save(spielstand);
        */


        /*
        // One to One relationShip
        Employee e = new Employee();
        e.name = "Herbert";

        Project p1 = new Project();
        p1.name = "Java-Hello World! - Basic";

        Project p2 = new Project();
        p2.name = "Java-Hello World! - Advanced";

        Project p3 = new Project();
        p3.name = "Java-Hello World! - Pro";

        p1.employee = e;
        p2.employee = e;
        p3.employee = e;

        employeeRepository.save(e);

        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);

        e.getProjects().add(p1);
        e.getProjects().add(p2);
        e.getProjects().add(p3);
        */

    }

    /**
     * Generiert Spieler, Spielstände und Spielergebnisse - die Ergebnisse können in vers. Ansichten des Frontends
     * betrachtet werden (z.b. in der Historie-View).
     */
    private void prepopulateTestData() {

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
