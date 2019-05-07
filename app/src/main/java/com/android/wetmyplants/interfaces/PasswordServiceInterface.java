package com.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<JsonObject> postNewPassExternal(
            @Field("Email") String Email,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("newpass/{token}")
    Call<JsonObject> postNewPassInternal(
            @Path("Token") String Token,
            @Field("Password") String Password
    );

}
