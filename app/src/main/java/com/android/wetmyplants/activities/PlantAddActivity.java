package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

public class PlantAddActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;

    EditText inputPlantName;
    EditText inputSpeciesName;
    EditText inputSensorNumber;

    private Plant newPlant;
    private String outPlantName;
    private String outSpeciesName;
    private String outSensorNumber;
    private double initWater = 0.0;
    private double initLight = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantadd);
        communicator = new Communicator();
        storedToken = sharedpref.getString("UserCredentials", "");

        inputPlantName = findViewById(R.id.plantAddNameInput);
        outPlantName = inputPlantName.getText().toString();
        inputSpeciesName = findViewById(R.id.speciesAddInput);
        outSpeciesName = inputSpeciesName.getText().toString();
        inputSensorNumber = findViewById(R.id.sensorSerialInput);
        outSensorNumber = inputSensorNumber.getText().toString();

        newPlant = new Plant(outPlantName, outSpeciesName, outSensorNumber, initWater, initLight);

        Button submitNewPlantBtn = findViewById(R.id.newPlantButton);
        submitNewPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptAddingPlant(storedToken, newPlant);
                Snackbar.make(v, "You have successfully add a plant", Snackbar.LENGTH_LONG).
                        setAction("Action", null).show();

                startActivity(new Intent(PlantAddActivity.this, PlantsActivity.class));
            }
        });
    }

    public void attemptAddingPlant(String inToken, Plant inPlant){
        communicator.plantAddPost(inToken, newPlant, new Callback<okhttp3.Response>(){
            @Override
            public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response){
                if(response.isSuccessful()) {
                    //do nothing
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
