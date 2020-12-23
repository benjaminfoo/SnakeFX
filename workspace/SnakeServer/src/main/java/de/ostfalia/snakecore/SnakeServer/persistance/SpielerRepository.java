package de.ostfalia.snakecore.SnakeServer.persistance;

import de.ostfalia.snakecore.model.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Benjamin Wulfert
 * The Player DAO (Data Access Object) returns every object within the RDBMS
 */
@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {
 
    public List<Spieler> findAllByName(String name);

    public Optional<Spieler> findByName(String name);

}
