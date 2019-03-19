package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RegisterInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> post(
            @Field("first_name_field") String FirstName,
            @Field("last_name_field") String LastName,
            @Field("phone_number_field") String Phone,
            @Field("email_field") String Email,
            @Field("password_field") String Password
    );
}
