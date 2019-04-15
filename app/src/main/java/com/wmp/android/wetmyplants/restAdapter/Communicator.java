package com.wmp.android.wetmyplants.restAdapter;

import com.google.gson.JsonObject;
import com.squareup.otto.Produce;
import com.wmp.android.wetmyplants.interfaces.PlantServiceInterface;
import com.wmp.android.wetmyplants.interfaces.UserServiceInterface;
import com.wmp.android.wetmyplants.interfaces.LoginInterface;
import com.wmp.android.wetmyplants.interfaces.PasswordServiceInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {

    private static final String TAG = "Communicator";
    private static final String TEST_SERVER_URL = "https://ypwmptest.azurewebsites.net/api/";
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
                    .baseUrl(TEST_SERVER_URL)
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
    public void forgotPasswordSendGridPost(String inEmail, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postSg(inEmail);
        call.enqueue(callback);
    }

    public void forgotPasswordRabbitmqPost(String inEmail, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postRmq(inEmail);
        call.enqueue(callback);
    }

    public void submitPinPost(String inPin, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postPin(inPin);
        call.enqueue(callback);
    }

    public void updatePasswordPost(String newPassword, Callback<JsonObject> callback){
        getRetrofitInstance();
        PasswordServiceInterface service = retrofit.create(PasswordServiceInterface.class);
        Call<JsonObject> call = service.postNewPass(newPassword);
        call.enqueue(callback);
    }

    /**using UserServiceInterface*/
    public void registerPost(String inFname, String inLname, String inPhone,String inEmail, String inPass,
                             Callback<JsonObject> callback){
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

    public void userUpdatePut(String inFname, String inLname, String inPhone,String inEmail, String inPass,
                               Callback<JsonObject> callback){
        getRetrofitInstance();
        UserServiceInterface service = retrofit.create(UserServiceInterface.class);
        Call<JsonObject> call = service.postUpdateUser(inFname, inLname, inPhone, inEmail, inPass);
        call.enqueue(callback);
    }

    /**using PlantServiceInterface*/
    public void plantListGet(String token, Callback<JsonObject> callback){
        getRetrofitInstance();
        PlantServiceInterface service = retrofit.create(PlantServiceInterface.class);
        Call<JsonObject> call = service.getPlantList(token);
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
