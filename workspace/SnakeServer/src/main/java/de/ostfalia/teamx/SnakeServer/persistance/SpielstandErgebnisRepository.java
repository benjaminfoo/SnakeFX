package de.ostfalia.teamx.SnakeServer.persistance;

import de.ostfalia.teamx.SnakeServer.model.Spielstand;
import de.ostfalia.teamx.SnakeServer.model.SpielstandErgebnis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Benjamin Wulfert
 */
@Repository
public interface SpielstandErgebnisRepository extends CrudRepository<SpielstandErgebnis, Long> {
 
}
