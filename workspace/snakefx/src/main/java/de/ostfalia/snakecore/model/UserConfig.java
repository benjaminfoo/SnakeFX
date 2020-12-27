package de.ostfalia.snakecore.model;

public class UserConfig {

    private String userName;
    private String pass;
    private String jwt;

    public UserConfig() {
    }

    public UserConfig(String userName, String pass, String jwt) {
        this.userName = userName;
        this.pass = pass;
        this.jwt = jwt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
