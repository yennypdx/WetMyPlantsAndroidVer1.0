package com.android.wetmyplants.interfaces;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.android.wetmyplants.model.Plant;

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
    Call<JsonArray> getPlantList(
            @Path("UserCredentials") String Token
    );

    @FormUrlEncoded
    @GET("plant/id/{token}")
    Call<JsonObject> getPlantDetail(
            @Path("UserCredentials") String Token
    );

    @FormUrlEncoded
    @POST("plant/add/{token}")
    Call<Response> postAddPlant(
            @Path("UserCredentials") String Token,
            @Body Plant plant
    );

    @FormUrlEncoded
    @PUT("plant/edit/{token}")
    Call<Response> putNewPlantData(
            @Path("UserCredentials") String Token,
            @Body Plant plant

    );

    @FormUrlEncoded
    @DELETE("plant/del/{token}")
    Call<Response> deletePlant(
            @Path("UserCredentials") String Token,
            @Field("PlantId") String PlantId
    );

}
