package de.ostfalia.snakecore.SnakeServer;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.SnakeServer.persistance.SpielstandErgebnisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpielstandErgebnisTest {

	@Autowired
	SpielerRepository spielerRepository;

	@Autowired
	SpielstandErgebnisRepository spielstandErgebnisRepository;

	@Test
	void Test_Save_Spielstand() {
		/*
		Spieler testSpieler = new Spieler(1L, "Test-Spieler", "Test-Passwort");
		Spieler testSpieler2 = new Spieler(2L, "Test-Spieler-2", "Test-Passwort-2");

		SpielstandErgebnis s = new SpielstandErgebnis(testSpieler, 37);

		spielstandErgebnisRepository.save(s);
		*/
    }

}
