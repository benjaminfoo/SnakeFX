package de.ostfalia.snakecore.SnakeServer.persistance;

import de.ostfalia.snakecore.model.SpielDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Benjamin Wulfert
 */
@Repository
public interface SpielDefinitionRepository extends JpaRepository<SpielDefinition, Long> {


}
