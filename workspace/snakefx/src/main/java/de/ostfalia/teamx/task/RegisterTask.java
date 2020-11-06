package de.ostfalia.teamx.task;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.model.Spieler;

public class RegisterTask {

    public void register(String username, String password){

        Spieler neuerSpieler = new Spieler(-1, username, password);
        Gson gson = new Gson();

        try {
            System.out.println("Register new player: " + gson.toJson(neuerSpieler));
            HttpResponse<String> res = Unirest
                    .post(ProjectEndpoints.URL_API_REGISTER)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(neuerSpieler))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        
    }

}
