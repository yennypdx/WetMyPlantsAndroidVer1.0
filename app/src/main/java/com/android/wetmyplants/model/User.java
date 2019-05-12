package com.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Id")
    @Expose
    private int Id;

    @SerializedName("FirstName")
    @Expose
    private String FirstName;

    @SerializedName("LastName")
    @Expose
    private String LastName;

    @SerializedName("Phone")
    @Expose
    private String Phone;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("Password")
    @Expose
    private String Password;

    public User(){}

    public User(String firstname, String lastname, String phone,
                String email, String password)
    {
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Phone = phone;
        this.Email = email;
        this.Password = password;
    }

    public User(String firstname, String lastname, String phone, String email)
    {
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Phone = phone;
        this.Email = email;
    }
    public User(int id, String firstname, String lastname, String phone, String email)
    {
        this.Id = id;
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Phone = phone;
        this.Email = email;
    }

    public User(int id, String firstname, String lastname, String phone,
                   String email, String password)
    {
        this.Id = id;
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Phone = phone;
        this.Email = email;
        this.Password = password;
    }

    public int getId() { return Id; }
    public void setId(int inId) { this.Id = inId; }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String inFName) { this.FirstName = inFName; }

    public String getLastName() { return LastName; }
    public void setLastName(String inLName) { this.LastName = inLName; }

    public String getPhone() { return Phone; }
    public void setPhone(String inPhone) { this.Phone = inPhone; }

    public String getEmail() { return Email; }
    public void setEmail(String inEmail) { this.Email = inEmail; }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
