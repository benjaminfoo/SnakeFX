package de.ostfalia.teamx;

import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.task.GetPlayerTask;
import org.junit.Test;

public class TaskTest {

    @Test
    public void endpointTest() throws UnirestException {
        new GetPlayerTask().getPlayer();
    }

}
