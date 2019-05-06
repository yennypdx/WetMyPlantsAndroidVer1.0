package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantEditActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    EditText inputName;
    EditText inputSpecies;
    String outNewPlantName;
    String outNewPlantSpecies;

    String storedPlantId;
    double storedCurrWater;
    double storedCurrLight;
    Plant updatedPlant;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantedit);
        communicator = new Communicator();
        gson = new Gson();

        storedToken = sharedpref.getString("UserCredentials", "");
        pullUserInformation(storedToken);

        inputName = findViewById(R.id.plantEditNameInput);
        outNewPlantName = inputName.getText().toString();
        inputSpecies = findViewById(R.id.plantEditSpeciesInput);
        outNewPlantSpecies = inputSpecies.getText().toString();

        Button updateBtn = findViewById(R.id.update_plant_info_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdatePlant(storedToken, updatedPlant);
                startActivity(new Intent(PlantEditActivity.this, PlantsActivity.class));
            }
        });
    }

    public void pullUserInformation(String inToken){
        communicator.plantDetailGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    String plantObject = response.body().getAsJsonObject().toString();
                    Plant userPlant = gson.fromJson(plantObject, Plant.class);

                    storedPlantId = userPlant.getSensorSerial();
                    storedCurrWater = userPlant.getCurrentWater();
                    storedCurrLight = userPlant.getCurrentLight();

                    updatedPlant = new Plant(storedPlantId, outNewPlantName, outNewPlantSpecies,
                            storedCurrWater, storedCurrLight);
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(
                        PlantEditActivity.this, DashboardActivity.class));
            }
        });
    }

    public void attemptUpdatePlant( String inToken, Plant inPlantObject){
        communicator.plantUpdatePut(inToken, inPlantObject, new Callback<okhttp3.Response>(){
                    @Override
                    public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response){
                        if(response.isSuccessful()) {
                                //do nothing, move on
                        }
                        else{
                            Log.e("Error Code", String.valueOf(response.code()));
                            Log.e("Error Body", response.errorBody().toString());
                            Toast.makeText(getApplicationContext(),
                                    "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<okhttp3.Response> call, Throwable t){
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
