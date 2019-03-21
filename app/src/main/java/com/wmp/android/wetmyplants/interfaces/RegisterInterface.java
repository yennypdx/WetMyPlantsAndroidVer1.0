package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RegisterInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> post(
            @Field("FirstName") String FirstName,
            @Field("LastName") String LastName,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("Password") String Password
    );
}
