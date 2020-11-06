package de.ostfalia.teamx;

public class ProjectEndpoints {

    public static String BASE_URL = "http://localhost:8080/";

    public static String HOST_URL_API = BASE_URL + "api/";

    public static String HOST_URL_API_LOGIN = HOST_URL_API + "login/";

    public static String HOST_URL_API_REGISTER = HOST_URL_API + "register/";

    public static String HOST_URL_API_SPIELER = HOST_URL_API + "spieler/";

    public static String HOST_URL_API_LOBBY = HOST_URL_API + "lobby/";

    public static void resetURLs(){
        HOST_URL_API = BASE_URL + "api/";

        HOST_URL_API_LOGIN = HOST_URL_API + "login/";

        HOST_URL_API_REGISTER = HOST_URL_API + "register/";

        HOST_URL_API_SPIELER = HOST_URL_API + "spieler/";

        HOST_URL_API_LOBBY = HOST_URL_API + "lobby/";
    }

}
