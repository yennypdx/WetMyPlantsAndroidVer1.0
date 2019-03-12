package com.wmp.android.wetmyplants.viewModel;

public class Login_ViewModel {

    private String Email_Login;
    private String Password_Login;

    public Login_ViewModel(String inEmail, String inPass){
        this.Email_Login = inEmail;
        this.Password_Login = inPass;
    }

    public String getEmail(){ return Email_Login; }
    public void setEmail(String inEmail) { Email_Login = inEmail; }

    public String getPassword(){ return Password_Login; }
    public void setPassword(String inPass) { Password_Login = inPass; }
}
