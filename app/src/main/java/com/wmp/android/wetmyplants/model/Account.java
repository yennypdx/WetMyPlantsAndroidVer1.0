package com.wmp.android.wetmyplants.model;

public class Account {

    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Email;
    private String Password;

    public Account(String firstname, String lastname, String phone,
                   String email, String password)
    {
        this.FirstName = firstname;
        this.LastName = lastname;
        this.PhoneNumber = phone;
        this.Email = email;
        this.Password = password;
    }

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
