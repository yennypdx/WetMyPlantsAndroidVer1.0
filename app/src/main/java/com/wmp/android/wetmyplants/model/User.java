package com.wmp.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("fname")
    @Expose
    private String FirstName;

    @SerializedName("lname")
    @Expose
    private String LastName;

    @SerializedName("phn")
    @Expose
    private String PhoneNumber;

    @SerializedName("email")
    @Expose
    private String Email;

    @SerializedName("pass")
    @Expose
    private String Password;

    public User(){}

    public User(String inFname, String inLname, String inPhone, String inEmail, String inPass)
    {
        this.FirstName = inFname;
        this.LastName = inLname;
        this.PhoneNumber = inPhone;
        this.Email = inEmail;
        this.Password = inPass;
    }
}

