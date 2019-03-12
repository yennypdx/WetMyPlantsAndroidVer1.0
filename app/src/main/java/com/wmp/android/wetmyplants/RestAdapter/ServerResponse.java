package com.wmp.android.wetmyplants.RestAdapter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerResponse implements Serializable{

    @SerializedName("returned_email")
    private String email;

    @SerializedName("returned_password")
    private String password;

    @SerializedName("message")
    private String message;

    @SerializedName("response_code")
    private int responseCode;

    public ServerResponse(String inEmail, String inPass, String inMessage, int inResCode){
        this.email = inEmail;
        this.password = inPass;
        this.message = inMessage;
        this.responseCode = inResCode;
    }

    public String getEmail(){ return email; }
    public void setEmail(String inEmail) { email = inEmail; }

    public String getPassword(){ return password; }
    public void setPassword(String inPass) { password = inPass; }

    public String getMessage(){ return message; }
    public void setMessage(String inMessage) { message = inMessage; }

    public int getResponseCode(){ return responseCode; }
    public void setResponseCode(int inResponse) { responseCode = inResponse; }

}
