package com.wmp.android.wetmyplants.restAdapter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.squareup.otto.Produce;
import com.wmp.android.wetmyplants.interfaces.LoginInterface;
import com.wmp.android.wetmyplants.interfaces.RegisterInterface;
import com.wmp.android.wetmyplants.viewModel.RegisterViewModel;

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

    public void loginPost(String inEmail, String inPass){
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        LoginInterface service = retrofit.create(LoginInterface.class);
        Call<JsonObject> call = service.post(inEmail, inPass);
        call.enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                // response.isSuccessful() is true if the response code is 2xx
                //BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG, "Success");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void registerPost(String inFname, String inLname, String inPhone, String inEmail,
                             String inPass){

        RegisterViewModel rvm = new RegisterViewModel(inFname, inLname, inPhone, inEmail, inPass);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        RegisterInterface service = retrofit.create(RegisterInterface.class);
        Call<JsonObject> call = service.post(rvm);
        call.enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                // response.isSuccessful() is true if the response code is 2xx
                //BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG, "Success");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
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
