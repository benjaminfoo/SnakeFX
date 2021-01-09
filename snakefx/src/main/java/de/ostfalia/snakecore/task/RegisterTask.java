package de.ostfalia.snakecore.task;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.model.Spieler;


/**
 * @author Benjamin Wulfert
 *
 * The RegisterTask is used in order to realize a mechanism for the registration of the player credentials.
 * The task sends a HTTP-POST request to the backend which contains JSON-formatted information like the username and the userpassword.
 * If the information are valid, the backend responds with a valid message.
 */
public class RegisterTask {

    public void register(String username, String password){

        Spieler neuerSpieler = new Spieler(-1L, username, password);
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
