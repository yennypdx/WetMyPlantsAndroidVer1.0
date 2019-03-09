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

    public int getId() { return id; }
    public void setId(int inId) { this.id = inId;}

    public String getFirstName() { return FirstName; }
    public void setFirstName(String inFName) { this.FirstName = inFName; }

    public String getLastName(){ return LastName; }
    public void setLastName(String inLName) { LastName = inLName; }

    public String getPhoneNumber(){ return PhoneNumber; }
    public void setPhoneNumber(String inPhone) { PhoneNumber = inPhone; }

    public String getEmail(){ return Email; }
    public void setEmail(String inEmail) { Email = inEmail; }

    public String getPassword(){ return Password; }
    public void setPassword(String inPass) { Password = inPass; }

}

