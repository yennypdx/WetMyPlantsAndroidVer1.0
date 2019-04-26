package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PasswordServiceInterface {

    @FormUrlEncoded
    @POST("forgotpass/sg/{email}")
    Call<Response> postSg(
            @Path("Email") String Email
    );

    @FormUrlEncoded
    @POST("forgotpass/rmq/{email}")
    Call<Response> postRmq(
            @Path("Email") String Email
    );

    @FormUrlEncoded
    @POST("submit/{pin}")
    Call<JsonObject> postPin(
            @Path("Pin") String Pin
    );

    @FormUrlEncoded
    @POST("newpass")
    Call<JsonObject> postNewPass(
            @Field("Password") String Password
    );



}
