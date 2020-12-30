package de.ostfalia.snakecore.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.model.RunningGame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Wulfert
 *
 * The GetGamesTask is responsible for the retrieval of active games from the web-server.
 */
public class GetGamesTask {

    public List<RunningGame> getSpiele() throws UnirestException {

        String url = ProjectEndpoints.URL_API_LOBBY;
        System.out.println(url);

        HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").asJson();
        String json = resJson.getBody().toString();

        System.out.println(json);

        List<RunningGame> spielDefinitions = new Gson().fromJson(json, new TypeToken<ArrayList<RunningGame>>() {}.getType());

        spielDefinitions.forEach(e -> System.out.println(e.toString()));

        return spielDefinitions;
    }

}
