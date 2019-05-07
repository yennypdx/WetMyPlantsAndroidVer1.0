package com.android.wetmyplants.interfaces;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.android.wetmyplants.model.Plant;

import java.util.ArrayList;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantServiceInterface {

    @FormUrlEncoded
    @GET("plant/{token}")
    Call<ArrayList<Plant>> getPlantList(
            @Path ("Token") String Token
    );

    @FormUrlEncoded
    @GET("plant/id/{token}")
    Call<JsonObject> getPlantDetail(
            @Path ("Token") String Token
    );

    @FormUrlEncoded
    @POST("plant/add/{token}")
    Call<Response> postAddPlant(
            @Path ("Token") String Token,
            @Body Plant plant
    );

    @FormUrlEncoded
    @PUT("plant/edit/{token}")
    Call<Response> putNewPlantData(
            @Path ("Token") String Token,
            @Body Plant plant

    );

    @FormUrlEncoded
    @DELETE("plant/del/{token}")
    Call<Response> deletePlant(
            @Path ("Token") String Token,
            @Field ("PlantId") String PlantId
    );

}
