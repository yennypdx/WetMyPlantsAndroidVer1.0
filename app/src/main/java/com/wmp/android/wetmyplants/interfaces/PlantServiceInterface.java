package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlantServiceInterface {

    @FormUrlEncoded
    @GET("plant/{token}")
    Call<JsonObject> getPlantList(
            @Query("Token") String Token
    );

    @FormUrlEncoded
    @GET("plant/id/{token}")
    Call<JsonObject> getPlantDetail(
            @Query("Token") String Token
    );

}
