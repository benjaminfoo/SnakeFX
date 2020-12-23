package de.ostfalia.snakecore.SnakeServer.persistance;

import de.ostfalia.snakecore.model.Spielstand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Benjamin Wulfert
 * The Player DAO (Data Access Object) returns every object within the RDBMS
 */
@Repository
public interface SpielstandRepository extends JpaRepository<Spielstand, Long> {

}
