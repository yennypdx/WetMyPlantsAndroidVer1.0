package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewPasswordInterface {

    @FormUrlEncoded
    @POST("newpass")
    Call<JsonObject> post(
            @Field("Token") String Token,
            @Field("Password") String Password
    );
}
