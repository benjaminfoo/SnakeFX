package de.ostfalia.teamx.SnakeServer;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class SnakeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnakeServerApplication.class, args);
	}

}
