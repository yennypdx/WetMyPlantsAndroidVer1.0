package com.wmp.android.wetmyplants.RestAdapter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginInterface {

    @FormUrlEncoded
    @POST("/api.php")
    Call<ServerResponse> post(
            @Field("method") String method,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("/api.php")
    Call<ServerResponse> get(
            @Query("method") String method,
            @Query("email") String email,
            @Query("password") String password
    );
}
