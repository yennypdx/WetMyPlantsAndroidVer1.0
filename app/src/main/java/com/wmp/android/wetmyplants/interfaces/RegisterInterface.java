package com.wmp.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.viewModel.RegisterViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RegisterInterface {

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("user/register")
    Call<JsonObject> post(@Body RegisterViewModel registerViewModel);
}
