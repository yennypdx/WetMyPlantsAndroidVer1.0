package com.wmp.android.wetmyplants.remote;

public class APIUtilities
{
    private APIUtilities(){};

    //TODO:change the URIs to the API match its controller
    public static final String API_URL_USER = "http://localhost:8080/";

    //TODO: add more services to handle the requests
    public static UserService getUserService()
    {
        return RetrofitClient.getClient(API_URL_USER).create(UserService.class);
    }

}
