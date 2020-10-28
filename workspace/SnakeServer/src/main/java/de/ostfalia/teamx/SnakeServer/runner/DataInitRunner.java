package de.ostfalia.teamx.SnakeServer.runner;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
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
 
    private SpielerRepository spielerRepository;
 
    @Autowired
    public DataInitRunner(SpielerRepository spielerRepository) {
        this.spielerRepository = spielerRepository;
    }
 
    @Override
    public void run(ApplicationArguments args) throws Exception {

        spielerRepository.save(new Spieler(-1, "test", "test"));
        spielerRepository.save(new Spieler(-1, "test2", "test2"));

    }
     
}
