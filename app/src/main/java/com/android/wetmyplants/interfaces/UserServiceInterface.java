package com.android.wetmyplants.interfaces;

import com.android.wetmyplants.model.User;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserServiceInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<String> postRegister(
            @Field("FirstName") String FirstName,
            @Field("LastName") String LastName,
            @Field("Phone") String Phone,
            @Field("Email") String Email,
            @Field("Password") String Password
    );

    @GET("user/{token}/")
    Call<JsonObject> getUser(
            @Path("token") String token
    );

    @PATCH("user/update/{token}/")
    Call<ResponseBody> patchUpdateUser(
            @Path("token") String token,
            @Body User user
    );

}
