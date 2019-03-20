package com.wmp.android.wetmyplants.viewModel;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterViewModel {

    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("LastName")
    private String LastName;

    @SerializedName("Phone")
    private String Phone;

    @SerializedName("Email")
    private String Email;

    @SerializedName("Password")
    private String Password;

    public RegisterViewModel(){}

    public RegisterViewModel(String inFn, String inLn, String inPh, String inEma, String inPas)
    {
        this.FirstName = inFn;
        this.LastName = inLn;
        this.Phone = inPh;
        this.Email = inEma;
        this.Password = inPas;
    }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String inFn) { this.FirstName = inFn; }

    public String getLastName(){ return LastName; }
    public void setLastName(String inLn) { LastName = inLn; }

    public String getPhone(){ return Phone; }
    public void setPhone(String inPh) { Phone = inPh; }

    public String getEmail(){ return Email; }
    public void setEmail(String inEma) { Email = inEma; }

    public String getPassword(){ return Password; }
    public void setPassword(String inPas) { Password = inPas; }

}
