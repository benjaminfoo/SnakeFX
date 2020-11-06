package de.ostfalia.teamx.task;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.teamx.ProjectEndpoints;
import de.ostfalia.teamx.shared.Spieler;
import javafx.concurrent.Task;
import org.apache.http.HttpStatus;


/**
 * @author Benjamin Wulfert
 *
 * The LoginTask is responsible for the transfer of the user credentials to the backend
 */
public class LoginTask extends Task<Spieler> {

    private String username, password;

    public LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Spieler call() throws UnirestException {

        System.out.println("Login -> ");

        Spieler neuerSpieler = new Spieler(-1, username, password);
        Gson gson = new Gson();


        HttpResponse<String> res = Unirest
                .post(ProjectEndpoints.URL_API_LOGIN)
                .header("Content-Type", "application/json")
                .body(gson.toJson(neuerSpieler))
                .asString();

        if(res.getStatus() != HttpStatus.SC_FORBIDDEN){
            String jsonWebToken = res.getHeaders().getFirst("Authorization");
            // TODO - do something with the jsonWebToken
            // Add this to the player, use it for further authorization
            return neuerSpieler;
        }


        return null;

    }
}
