package com.wmp.android.wetmyplants.model;

import java.util.Date;

public class AccountToken {

    private String AccountEmail;
    private String Token;

    public AccountToken(String inEmail, String inToken)
    {
        this.AccountEmail = inEmail;
        this.Token = inToken;
    }

    public String getAccountEmail() { return AccountEmail; }
    public void setAccountEmail(String inEmail) { this.AccountEmail = inEmail; }

    public String getToken() { return Token; }
    public void setToken(String inToken) { this.Token = inToken; }

}
