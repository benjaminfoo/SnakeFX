package de.ostfalia.teamx.SnakeServer.persistance;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.model.Spielhistorie;
import de.ostfalia.teamx.SnakeServer.model.Spielrunde;
import de.ostfalia.teamx.SnakeServer.model.Spielstand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Benjamin Wulfert
 * The Player DAO (Data Access Object) returns every object within the RDBMS
 */
@Repository
public interface SpielstandRepository extends CrudRepository<Spielstand, Long> {
 
}
