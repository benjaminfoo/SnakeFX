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
		Spieler testSpieler = new Spieler(-1, "Test-Spieler", "Test-Passwort");
		Spieler testSpieler2 = new Spieler(-1, "Test-Spieler-2", "Test-Passwort-2");

		// Def. eines Ergebnisses
		SpielstandErgebnis s = new SpielstandErgebnis(-1, testSpieler, 37);

		// Speichern des Ergebnisses
		// spielstandErgebnisRepository.save(s);

		// Definition eines Spielstands
		Spielstand spielstand = new Spielstand();
		spielstand.getErgebnisse().add(s);

		// Speichern des Spielstands
		spielstandRepository.save(spielstand);
	}

}
