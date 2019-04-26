package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.model.Account;
import com.wmp.android.wetmyplants.model.Plant;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserServiceInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> post(
            @Field("FirstName") String FirstName,
            @Field("LastName") String LastName,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @GET("user/{token}")
    Call<JsonObject> getUser(
            @Query("Token") String Token
    );

    @FormUrlEncoded
    @POST("user/update")
    Call<Response> postUpdateUser(
            @Path("Token") String Token,
            @Body Account account
    );

}
