package com.wmp.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Id")
    @Expose
    private Integer Id;

    @SerializedName("FirstName")
    @Expose
    private String FirstName;

    @SerializedName("LastName")
    @Expose
    private String LastName;

    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("Password")
    @Expose
    private String Password;

    public Integer getId() { return Id; }
    public void setId(Integer inId) { this.Id = inId;}

    public String getFirstName() { return FirstName; }
    public void setFirstName(String inFName) { this.FirstName = inFName; }

    public String getLastName() { return LastName; }
    public void setLastName(String inLName) { this.LastName = inLName; }

    public String getPhoneNumber() { return PhoneNumber; }
    public void setPhoneNumber(String inPhone) { this.PhoneNumber = inPhone; }

    public String getEmail() { return Email; }
    public void setEmail(String inEmail) { this.Email = inEmail; }

    public String getPassword() { return Password; }
    public void setPassword(String inPass) { this.Password = inPass; }
}
