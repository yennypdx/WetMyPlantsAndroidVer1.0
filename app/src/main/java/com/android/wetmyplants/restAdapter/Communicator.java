package com.android.wetmyplants.restAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.otto.Produce;

import com.android.wetmyplants.interfaces.PlantServiceInterface;
import com.android.wetmyplants.interfaces.UserServiceInterface;
import com.android.wetmyplants.interfaces.LoginInterface;
import com.android.wetmyplants.interfaces.PasswordServiceInterface;

import com.android.wetmyplants.model.Account;
import com.android.wetmyplants.model.Plant;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {

    private static final String TAG = "Communicator";
    private static final String TEAM_SERVER_URL = "https://wetmyplants.azurewebsites.net/api";
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
                    .baseUrl(TEAM_SERVER_URL)
                    .build();
        }
        return retrofit;
    }

    /**using LoginInterface*/
    public void loginPost(String inEmail, String inPass, Callback<JsonObject> callback){
        getRetrofitInstance();
        LoginInterface service = retrofit.create(LoginInterface.class);
        Call<JsonObject> call = service.post(inEmail, inPass);
        call.enqueue(callback);
    }

    /**using PasswordServiceInterface*/
    public void forgotPasswordSendGridPost(String inEmail, Callback<Response> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<Response> call = service.postSg(inEmail);
        call.enqueue(callback);
    }

    public void forgotPasswordRabbitmqPost(String inEmail, Callback<Response> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<Response> call = service.postRmq(inEmail);
        call.enqueue(callback);
    }

    public void submitPinPost(String inPin, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postPin(inPin);
        call.enqueue(callback);
    }

    public void updatePasswordExternalPost(String email, String newPassword, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postNewPassExternal(email, newPassword);
        call.enqueue(callback);
    }

    public void updatePasswordInternalPost(String token, String newPassword, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postNewPassInternal(token, newPassword);
        call.enqueue(callback);
    }

    /**using UserServiceInterface*/
    public void registerPost(String inFname, String inLname, String inPhone,String inEmail,
                             String inPass, Callback<JsonObject> callback){
        getRetrofitInstance();
        UserServiceInterface service = retrofit.create(UserServiceInterface.class);
        Call<JsonObject> call = service.post(inFname, inLname, inPhone, inEmail, inPass);
        call.enqueue(callback);
    }

    public void userDetailGet(String token, Callback<JsonObject> callback){
        getRetrofitInstance();
        UserServiceInterface service = retrofit.create(UserServiceInterface.class);
        Call<JsonObject> call = service.getUser(token);
        call.enqueue(callback);
    }

    public void userUpdatePut(String token, Account user, Callback<Response> callback){
        getRetrofitInstance();
        UserServiceInterface service = retrofit.create(UserServiceInterface.class);
        Call<Response> call = service.postUpdateUser(token, user);
        call.enqueue(callback);
    }

    /**using PlantServiceInterface*/
    public void plantListGet(String token, Callback<ArrayList<Plant>> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<ArrayList<Plant>> call = service.getPlantList(token);
        call.enqueue(callback);
    }

    public void plantAddPost(String token, Plant inPlant, Callback<Response> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<Response> call = service.postAddPlant(token, inPlant);
        call.enqueue(callback);
    }

    public void plantDetailGet(String token, Callback<JsonObject> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<JsonObject> call = service.getPlantDetail(token);
        call.enqueue(callback);
    }

    public void plantUpdatePut(String token, Plant plant, Callback<Response> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<okhttp3.Response> call = service.putNewPlantData(token, plant);
        call.enqueue(callback);
    }

    public void plantDelete(String token, String plantId, Callback<okhttp3.Response> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<okhttp3.Response> call = service.deletePlant(token, plantId);
        call.enqueue(callback);
    }

    /**helper methods*/
    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}
