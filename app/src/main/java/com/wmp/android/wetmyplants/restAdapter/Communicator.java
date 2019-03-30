package com.wmp.android.wetmyplants.restAdapter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.squareup.otto.Produce;
import com.wmp.android.wetmyplants.interfaces.LoginInterface;
import com.wmp.android.wetmyplants.interfaces.RegisterInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {

    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://192.168.1.6:5000/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, to get connection logs
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(SERVER_URL)
                    .build();
        }
        return retrofit;
    }

    public void loginPost(String inEmail, String inPass, Callback<JsonObject> callback){
        getRetrofitInstance();
        LoginInterface service = retrofit.create(LoginInterface.class);
        Call<JsonObject> call = service.post(inEmail, inPass);
        call.enqueue(callback);
    }

    public void registerPost(String inFname, String inLname, String inPhone,String inEmail, String inPass,
                             Callback<JsonObject> callback){
        getRetrofitInstance();
        RegisterInterface service = retrofit.create(RegisterInterface.class);
        Call<JsonObject> call = service.post(inFname, inLname, inPhone, inEmail, inPass);
        call.enqueue(callback);
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}
