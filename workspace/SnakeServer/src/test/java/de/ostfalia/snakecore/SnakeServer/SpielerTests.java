package de.ostfalia.snakecore.SnakeServer;

import de.ostfalia.snakecore.SnakeServer.persistance.SpielerRepository;
import de.ostfalia.snakecore.model.Spieler;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpielerTests {

	@Autowired
	SpielerRepository spielerRepository;

	@Test
	void Test_Save_Player_To_Database_And_Retrieve_It() {

		// Create a new player
		Spieler testSpieler = new Spieler(1L, "Test-Spieler", "Test-Passwort");

		// save it to the database
		spielerRepository.save(testSpieler);

		// retrieve it
		Spieler testSpielerAusDB = spielerRepository.findByName(testSpieler.name);

		// check that its present
		Assert.assertTrue(testSpielerAusDB != null);

		// check that the contents are equal
		Assert.assertEquals(testSpieler.name, testSpielerAusDB.name);
		Assert.assertEquals(testSpieler.pass, testSpielerAusDB.pass);

	}

}
