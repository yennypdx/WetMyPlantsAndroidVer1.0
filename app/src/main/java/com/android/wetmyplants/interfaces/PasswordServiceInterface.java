package com.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PasswordServiceInterface {

    @FormUrlEncoded
    @POST("forgotpass/sg")
    Call<ResponseBody> postSg(
            @Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("forgotpass/rmq")
    Call<ResponseBody> postRmq(
            @Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("pin/confirm")
    Call<ResponseBody> postPin(
            @Field("Pin") int Pin,
            @Field("Email") String Email
    );

    @FormUrlEncoded
    @POST("newpass/out")
    Call<String> postNewPassExternal(
            @Field("Email") String Email,
            @Field("Password") String Password
    );

    @PATCH("newpass/in")
    Call<ResponseBody> postNewPassInternal(
            @Field("Email") String Email,
            @Field("Password") String Password,
            @Field("ConfirmPassword") String ConfirmPassword
    );

}
