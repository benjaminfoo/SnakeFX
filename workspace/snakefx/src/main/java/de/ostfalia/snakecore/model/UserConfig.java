package de.ostfalia.snakecore.model;

public class UserConfig {

    private String userName;
    private String jwt;

    public UserConfig() {
    }

    public UserConfig(String userName, String jwt) {
        this.userName = userName;
        this.jwt = jwt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
