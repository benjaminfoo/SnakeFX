package de.ostfalia.snakecore;

/**
 * @Author Benjamin Wulfert
 * The Common URLS within the project
 */
public class ProjectEndpoints {

    // The base url of the backend
    // public static String URL_BASE = "http://localhost:13373/";
    public static String URL_BASE = "http://192.168.178.32:13373/";

    // The path to the api
    public static final String API_PATH = "api/";

    // These are the API endpoints - not useable without the BASE_URL + API_PATH
    public static final String API_ENDPOINT_LOGIN = "login/";
    public static final String API_ENDPOINT_REGISTER = "register/";
    public static final String API_ENDPOINT_SPIELER = "spieler/";
    public static final String API_ENDPOINT_LOBBY = "lobby/";
    public static final String API_ENDPOINT_HISTORIE = "historie/";

    // http://localhost:13373/api/login/
    public static String URL_API_LOGIN = URL_BASE + API_PATH + API_ENDPOINT_LOGIN;

    // http://localhost:13373/api/register/
    public static String URL_API_REGISTER = URL_BASE + API_PATH + API_ENDPOINT_REGISTER;

    // http://localhost:13373/api/spieler/
    public static String URL_API_SPIELER = URL_BASE + API_PATH + API_ENDPOINT_SPIELER;

    // http://localhost:13373/api/lobby/
    public static String URL_API_LOBBY = URL_BASE + API_PATH + API_ENDPOINT_LOBBY;

    // http://localhost:13373/api/historie/
    public static String URL_API_HISTORIE = URL_BASE + API_PATH + API_ENDPOINT_HISTORIE;

    /**
     * The method gets called before logging in - so any change to the paths will be reflected
     */
    public static void resetURLs(String newBaseUrl) {
        URL_BASE = newBaseUrl;

        System.out.println("Changing url to: " + URL_BASE);
        System.out.println("Changing url to: " + URL_BASE);
        System.out.println("Changing url to: " + URL_BASE);
        System.out.println("Changing url to: " + URL_BASE);

        // http://localhost:13373/api/login/
        URL_API_LOGIN = URL_BASE + API_PATH + API_ENDPOINT_LOGIN;

        // http://localhost:13373/api/register/
        URL_API_REGISTER = URL_BASE + API_PATH + API_ENDPOINT_REGISTER;

        // http://localhost:13373/api/spieler/
        URL_API_SPIELER = URL_BASE + API_PATH + API_ENDPOINT_SPIELER;

        // http://localhost:13373/api/lobby/
        URL_API_LOBBY = URL_BASE + API_PATH + API_ENDPOINT_LOBBY;

        // this replaces the hardcoded path (localhost) and the protocol
        URL_STOMP_BASE = URL_BASE.replace("http", "ws") + "snakeserver";
    }

    // =============================
    // RELATED TO STOMP
    // =============================

    // The STOMP server base path
    public static String URL_STOMP_BASE = "ws://localhost:13373/snakeserver";

    // ws://localhost:13373/stomp/
    public static final String STOMP_BROKER_TOPIC = "/topic";
    public static final String STOMP_TOPIC_MESSAGES = "/topic/messages";

    public static final String STOMP_APP_PREFIX = "/app";
    public static final String STOMP_MESSAGE_MAPPING_CHAT = "/chat";
    public static final String STOMP_DESTINATION_CHAT = STOMP_APP_PREFIX + STOMP_MESSAGE_MAPPING_CHAT;

    // the url which points to the stomp path related to chat messages
    // public static String URL_STOMP_BASE = URL_BASE.replace("http", "ws") + STOMP_MESSAGE_MAPPING_CHAT;




}
