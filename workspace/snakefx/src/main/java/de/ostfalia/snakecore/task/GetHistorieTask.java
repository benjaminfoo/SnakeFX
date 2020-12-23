package de.ostfalia.snakecore.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.model.Spielstand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 * <p>
 * The GetHistorieTask is responsible for the retrieval of all played games from the web-server.
 */
public class GetHistorieTask {

    public List<Spielstand> getSpielstaende() throws UnirestException {

        String url = ProjectEndpoints.URL_API_HISTORIE;
        System.out.println(url);

        HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").asJson();
        String json = resJson.getBody().toString();

        System.out.println(json);

        List<Spielstand> spielstaende = new Gson().fromJson(json, new TypeToken<ArrayList<Spielstand>>() {
        }.getType());

        spielstaende.forEach(e -> System.out.println(e.toString()));

        return spielstaende;
    }

}
