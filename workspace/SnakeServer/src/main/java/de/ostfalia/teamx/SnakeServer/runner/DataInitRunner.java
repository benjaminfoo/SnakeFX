package de.ostfalia.teamx.SnakeServer.runner;

import de.ostfalia.teamx.SnakeServer.persistance.*;
import de.ostfalia.teamx.SnakeServer.testmodel.Chef;
import de.ostfalia.teamx.SnakeServer.testmodel.Employee;
import de.ostfalia.teamx.SnakeServer.testmodel.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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
    private ChefRepository chefRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*
        // Definition Test-Spieler
        Spieler testSpieler = new Spieler(1L, "Test-Spieler", "Test-Passwort");
        Spieler testSpieler2 = new Spieler(2L, "Test-Spieler-2", "Test-Passwort-2");

        spielerRepository.save(testSpieler);
        spielerRepository.save(testSpieler2);

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

    }

}
