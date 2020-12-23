package de.ostfalia.snakecore.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.model.Spieler;

import java.util.ArrayList;
import java.util.List;

public class PostNewGame {

    public List<Spieler> getPlayer() throws UnirestException {

        String url = ProjectEndpoints.URL_API_SPIELER;

        HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").asJson();
        String json = resJson.getBody().toString();

        System.out.println(json);

        List<Spieler> spieler = new Gson().fromJson(json, new TypeToken<ArrayList<Spieler>>() {}.getType());

        spieler.forEach(e -> System.out.println(e.toString()));

        return spieler;
    }

}
