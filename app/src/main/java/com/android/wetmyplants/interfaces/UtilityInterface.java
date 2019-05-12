package com.android.wetmyplants.interfaces;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilityInterface {

    @FormUrlEncoded
    @POST("notif/{token}")
    Call<Response> postNotifStatus(
            @Path("token") String token,
            @Field("Status") int Status
    );
}
