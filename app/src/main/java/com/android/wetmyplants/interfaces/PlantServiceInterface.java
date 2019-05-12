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

public interface PlantServiceInterface {

    @GET("plant/{token}/")
    Call<ArrayList<Plant>> getPlantList(
            @Path("token") String token
    );

    @GET("plant/id/{token}/")
    Call<JsonObject> getPlantDetail(
            @Path("token") String token
    );

    @FormUrlEncoded
    @POST("plant/add/{token}/")
    Call<Response> postAddPlant(
            @Path("token") String token,
            @Body Plant plant
    );

    @FormUrlEncoded
    @PUT("plant/edit/{token}/")
    Call<Response> putNewPlantData(
            @Path("token") String token,
            @Field("Nickname") String Nickname,
            @Field("Id") String Id
    );

    @FormUrlEncoded
    @DELETE("plant/del/{token}/{plantid}/")
    Call<Response> deletePlant(
            @Path("token") String token,
            @Path("plantid") String plantid
    );

}
