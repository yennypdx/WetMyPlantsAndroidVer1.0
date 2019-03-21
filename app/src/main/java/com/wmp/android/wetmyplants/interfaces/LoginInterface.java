package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.restAdapter.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginInterface {

    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> post(
            @Field("Email") String Email,
            @Field("Password") String Password
    );
}
