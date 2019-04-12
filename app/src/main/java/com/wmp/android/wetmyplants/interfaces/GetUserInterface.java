package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface GetUserInterface {

    @FormUrlEncoded
    @GET("getusrdetail")
    Call<JsonObject> get(
            @Field("Token") String Token
    );
}
