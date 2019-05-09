package com.android.wetmyplants.interfaces;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    @FormUrlEncoded
    @POST("login")
    Call<String> post(
            @Field("Email") String Email,
            @Field("Password") String Password
    );
}
