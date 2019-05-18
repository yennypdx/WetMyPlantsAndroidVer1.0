package com.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;
import com.android.wetmyplants.model.Plant;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlantServiceInterface {

    @GET("plant/{token}/")
    Call<List<Plant>> getPlantList(
            @Path("token") String token
    );

    @GET("plant/id/{id}/")
    Call<JsonObject> getPlantDetail(
            @Path("id") String id
    );

    @POST("plant/add/{token}/")
    Call<ResponseBody> postAddPlant(
            @Path("token") String token,
            @Body Plant plant
    );

    @PATCH("plant/edit/{token}/")
    Call<ResponseBody> putNewPlantData(
            @Path("token") String token,
            @Body Plant plant
    );

    @FormUrlEncoded
    @DELETE("plant/del/{token}/{plantid}/")
    Call<ResponseBody> deletePlant(
            @Path("token") String token,
            @Path("plantid") String plantid
    );

}
