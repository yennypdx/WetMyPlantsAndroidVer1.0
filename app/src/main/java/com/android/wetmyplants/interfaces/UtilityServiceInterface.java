package com.android.wetmyplants.interfaces;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilityServiceInterface {

    @FormUrlEncoded
    @POST("user/set/notif/{token}")
    Call<ResponseBody> postNotifStatus(
            @Path("token") String token,
            @Field("status") int status
    );

    @GET("user/notif/{token}/")
    Call<ResponseBody> getNotificationPref(
            @Path("token") String token
    );
}
