package de.ostfalia.teamx.SnakeServer.persistance;

import de.ostfalia.teamx.SnakeServer.testmodel.Chef;
import de.ostfalia.teamx.SnakeServer.testmodel.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRepository extends JpaRepository<Chef, Long> {
}
