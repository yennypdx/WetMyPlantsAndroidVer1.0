package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PasswordServiceInterface {

    @FormUrlEncoded
    @POST("forgotpass/sg/{email}")
    Call<JsonObject> postSg(
            @Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("forgotpass/rmq/{email}")
    Call<JsonObject> postRmq(
            @Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("submit/{pin}")
    Call<JsonObject> postPin(
            @Field("Pin") String Pin
    );

    @FormUrlEncoded
    @POST("newpass")
    Call<JsonObject> postNewPass(
            @Field("Password") String Password
    );

}
