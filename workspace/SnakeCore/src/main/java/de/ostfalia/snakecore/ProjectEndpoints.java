package de.ostfalia.snakecore;

/**
 * @Author Benjamin Wulfert
 * The Common URLS within the project
 */
public class ProjectEndpoints {

    // The base url of the backend
    public static String BASE_URL = "http://localhost:8080/";

    // The path to the api
    public static final String API_PATH = "api/";

    // These are the API endpoints - not useable without the BASE_URL + API_PATH
    public static final String API_ENDPOINT_LOGIN = "login/";
    public static final String API_ENDPOINT_REGISTER = "register/";
    public static final String API_ENDPOINT_SPIELER = "spieler/";
    public static final String API_ENDPOINT_LOBBY = "lobby/";
    public static final String API_ENDPOINT_HISTORIE = "historie/";

    // http://localhost:8080/api/login/
    public static String URL_API_LOGIN = BASE_URL + API_PATH + API_ENDPOINT_LOGIN;

    // http://localhost:8080/api/register/
    public static String URL_API_REGISTER = BASE_URL + API_PATH + API_ENDPOINT_REGISTER;

    // http://localhost:8080/api/spieler/
    public static String URL_API_SPIELER = BASE_URL + API_PATH + API_ENDPOINT_SPIELER;

    // http://localhost:8080/api/lobby/
    public static String URL_API_LOBBY = BASE_URL + API_PATH + API_ENDPOINT_LOBBY;

    // http://localhost:8080/api/historie/
    public static String URL_API_HISTORIE = BASE_URL + API_PATH + API_ENDPOINT_HISTORIE;

    public static void resetURLs() {
        // http://localhost:8080/api/login/
        URL_API_LOGIN = BASE_URL + API_PATH + API_ENDPOINT_LOGIN;

        // http://localhost:8080/api/register/
        URL_API_REGISTER = BASE_URL + API_PATH + API_ENDPOINT_REGISTER;

        // http://localhost:8080/api/spieler/
        URL_API_SPIELER = BASE_URL + API_PATH + API_ENDPOINT_SPIELER;

        // http://localhost:8080/api/lobby/
        URL_API_LOBBY = BASE_URL + API_PATH + API_ENDPOINT_LOBBY;
    }

    // =============================
    // RELATED TO STOMP
    // =============================

    // The STOMP server base path

    // ws://localhost:8080/stomp/
    public static final String STOMP_BROKER_TOPIC = "/topic";
    public static final String STOMP_TOPIC_MESSAGES = "/topic/messages";
    public static final String STOMP_APP_PREFIX = "/app";
    public static final String STOMP_MESSAGE_MAPPING_CHAT = "/chat";
    public static final String STOMP_DESTINATION_CHAT = STOMP_APP_PREFIX + STOMP_MESSAGE_MAPPING_CHAT;

    public static String URL_STOMP_BASE = BASE_URL.replace("http", "ws") + STOMP_MESSAGE_MAPPING_CHAT;




}
