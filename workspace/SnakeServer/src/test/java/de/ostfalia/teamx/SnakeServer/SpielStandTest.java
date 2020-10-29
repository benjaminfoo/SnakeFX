package de.ostfalia.teamx.SnakeServer;

import de.ostfalia.teamx.SnakeServer.model.Spieler;
import de.ostfalia.teamx.SnakeServer.model.Spielstand;
import de.ostfalia.teamx.SnakeServer.model.SpielstandErgebnis;
import de.ostfalia.teamx.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandErgebnisRepository;
import de.ostfalia.teamx.SnakeServer.persistance.SpielstandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpielStandTest {

	@Autowired
	SpielerRepository spielerRepository;

	@Autowired
	SpielstandRepository spielstandRepository;

	@Autowired
	SpielstandErgebnisRepository spielstandErgebnisRepository;

	@Test
	void Test_Save_Spielstand() {

		// Definition Test-Spieler
		Spieler testSpieler = new Spieler(1L, "Test-Spieler", "Test-Passwort");
		Spieler testSpieler2 = new Spieler(2L, "Test-Spieler2", "Test-Passwort");

		// Def. eines Ergebnisses
		SpielstandErgebnis s = new SpielstandErgebnis(testSpieler, 37);
		SpielstandErgebnis s2 = new SpielstandErgebnis(testSpieler2, 100);

		// Speichern des Ergebnisses
		// spielstandErgebnisRepository.save(s);

		// Definition eines Spielstands
		Spielstand spielstand = new Spielstand();
		spielstand.getErgebnisse().add(s);
		spielstand.getErgebnisse().add(s2);

		// Speichern des Spielstands
		spielstandRepository.saveAndFlush(spielstand);
	}

}
