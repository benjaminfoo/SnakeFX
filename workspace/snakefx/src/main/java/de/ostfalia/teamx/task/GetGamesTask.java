package de.ostfalia.teamx.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.model.SpielDefinition;
import de.ostfalia.teamx.model.Spieler;

import java.util.ArrayList;
import java.util.List;

public class GetGamesTask {

    public List<SpielDefinition> getSpiele() throws UnirestException {

        String url = ProjectEndpoints.HOST_URL_API_LOBBY;

        HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").asJson();
        String json = resJson.getBody().toString();

        System.out.println(json);

        List<SpielDefinition> spielDefinitions = new Gson().fromJson(json, new TypeToken<ArrayList<SpielDefinition>>() {}.getType());

        spielDefinitions.forEach(e -> System.out.println(e.toString()));

        return spielDefinitions;
    }

}
