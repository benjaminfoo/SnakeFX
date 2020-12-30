package de.ostfalia.snakecore.task;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.ostfalia.snakecore.ProjectEndpoints;
import de.ostfalia.snakecore.model.Spieler;
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
    protected Spieler call()  {

        System.out.println("Login -> ");

        Spieler neuerSpieler = new Spieler(-1L, username, password);
        Gson gson = new Gson();

        HttpResponse<String> res = null;
        try {
            res = Unirest
                    .post(ProjectEndpoints.URL_API_LOGIN)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(neuerSpieler))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            failed();
            cancel() ;
        }


        if(res.getStatus() == HttpStatus.SC_BAD_REQUEST){
            failed();
            cancel() ;
        }

        if(res.getStatus() != HttpStatus.SC_FORBIDDEN || res.getStatus() == HttpStatus.SC_BAD_REQUEST){
            succeeded();
            String jsonWebToken = res.getHeaders().getFirst("Authorization");
            // TODO - do something with the jsonWebToken
            // Add this to the player, use it for further authorization
            return neuerSpieler;
        }



        return null;

    }
}
