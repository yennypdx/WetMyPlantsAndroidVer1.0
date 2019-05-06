package com.android.wetmyplants.model;

public class UserCredentials {

    private int id;
    private String email;
    private String token;

    public UserCredentials() {}

    public UserCredentials(String inEmail, String inToken)
    {
        this.email = inEmail;
        this.token = inToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int inId) {
        this.id = inId;
    }

    public String getEmail() { return email; }
    public void setEmail(String inEmail) { this.email = inEmail; }

    public String getToken() { return token; }
    public void setToken(String inToken) { this.token = inToken; }

}
