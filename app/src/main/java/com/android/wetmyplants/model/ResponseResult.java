package com.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseResult {
    @SerializedName("Id")
    @Expose
    private Integer Id;

    @SerializedName("UserCredentials")
    @Expose
    private String Token;

    @SerializedName("Email")
    @Expose
    private String Email;

    public Integer getId() { return Id; }
    public void setId(Integer inId) { this.Id = inId; }

    public String getToken() { return Token; }
    public void setToken(String inToken) { this.Token = inToken; }

    public  String getEmail() { return Email; }
    public void setEmail(String inEmail) { this.Email = inEmail; }
}
