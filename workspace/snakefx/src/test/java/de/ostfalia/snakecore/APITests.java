package de.ostfalia.snakecore;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.model.SpielDefinition;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.task.GetGamesTask;
import de.ostfalia.snakecore.task.GetPlayerTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class APITests {


    @Test
    /**
     * Checks if the front-end can:
     *  - access the backend
     *  - consume the /api/spieler endpoint
     *  - get all of the test-spieler within the database
     */
    public void endpointTest_GetSpieler() throws UnirestException {
        List<Spieler> spieler = new GetPlayerTask().getPlayer();
        Assert.assertEquals(2, spieler.size());
    }
    @Test
    /**
     * Checks if the front-end can:
     *  - access the backend
     *  - consume the /api/spieler endpoint
     *  - get all of the test-spieler within the database
     */
    public void endpointTest_GetSpiele() throws UnirestException {
        List<SpielDefinition> spiele = new GetGamesTask().getSpiele();

    }

}
