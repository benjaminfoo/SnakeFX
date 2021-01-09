package de.ostfalia.snakecore.SnakeServer.persistance;

import de.ostfalia.snakecore.model.Spielregel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Benjamin Wulfert
 *
 * CRUD-Repository for ORM / RDBMS related operations for the type: {@link SpielregelRepository}
 */
@Repository
public interface SpielregelRepository extends JpaRepository<Spielregel, Long> {


}
