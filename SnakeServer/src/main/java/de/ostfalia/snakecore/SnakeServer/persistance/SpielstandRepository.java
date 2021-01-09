package de.ostfalia.snakecore.SnakeServer.persistance;

import de.ostfalia.snakecore.model.Spielstand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Benjamin Wulfert
 *
 * CRUD-Repository for ORM / RDBMS related operations for the type: {@link Spielstand}
 */
@Repository
public interface SpielstandRepository extends JpaRepository<Spielstand, Long> {

}
