package de.ostfalia.teamx.SnakeServer;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.model.Spielstand;
import de.ostfalia.teamx.SnakeServer.model.SpielstandErgebnis;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandErgebnisRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandRepository;
import lombok.var;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class SpielstandErgebnisTest {

	@Autowired
	SpielerRepository spielerRepository;

	@Autowired
	SpielstandErgebnisRepository spielstandErgebnisRepository;

	@Test
	void Test_Save_Spielstand() {

		Spieler testSpieler = new Spieler(-1, "Test-Spieler", "Test-Passwort");
		Spieler testSpieler2 = new Spieler(-1, "Test-Spieler-2", "Test-Passwort-2");

		SpielstandErgebnis s = new SpielstandErgebnis(-1, testSpieler, 37);

		spielstandErgebnisRepository.save(s);
	}

}
