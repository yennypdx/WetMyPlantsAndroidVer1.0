package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.restAdapter.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterInterface {

    @FormUrlEncoded
    @POST("user/register")
    Call<JsonObject> post(
            @Field("first_name_field") String first_name,
            @Field("last_name_field") String last_name,
            @Field("phone_number_field") String phone_number,
            @Field("email_field") String email,
            @Field("password_field") String password
    );

    @GET("users/all")
    Call<JsonObject> get(
            @Query("first_name_field") String first_name,
            @Query("last_name_field") String last_name,
            @Query("phone_number_field") String phone_number,
            @Query("email_field") String email,
            @Query("password_field") String password
     );
}
