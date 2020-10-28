package de.ostfalia.teamx.task;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.model.Spieler;

import java.util.ArrayList;
import java.util.List;

public class GetPlayerTask {

    public void getPlayer() throws UnirestException {
        // String url = ShopApplication.API_HOST + "/products";
        String url = "http://localhost:8080/api/spieler/";

        HttpResponse<JsonNode> resJson = Unirest.get(url).header("Accept", "application/json").asJson();
        String json = resJson.getBody().toString();

        System.out.println(json);

        List<Spieler> products = new Gson().fromJson(json, new TypeToken<ArrayList<Spieler>>() {}.getType());

        products.forEach(e -> System.out.println(e.toString()));
    }

}
