package com.android.wetmyplants.model;

public class Account {

    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Email;
    private String Id;

    public Account(String firstname, String lastname, String phone,
                   String email, String id)
    {
        this.FirstName = firstname;
        this.LastName = lastname;
        this.PhoneNumber = phone;
        this.Email = email;
        this.Id = id;
    }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String inFName) { this.FirstName = inFName; }

    public String getLastName() { return LastName; }
    public void setLastName(String inLName) { this.LastName = inLName; }

    public String getPhoneNumber() { return PhoneNumber; }
    public void setPhoneNumber(String inPhone) { this.PhoneNumber = inPhone; }

    public String getEmail() { return Email; }
    public void setEmail(String inEmail) { this.Email = inEmail; }

    public String getId() { return Id; }
    public void setId(String inId) { this.Id = inId; }
}
