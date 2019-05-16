package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantAddActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;

    EditText inputPlantName;
    EditText inputSpeciesName;
    EditText inputSensorNumber;

    Plant newPlant;
    String outPlantName;
    int outSpeciesId;
    String outSensorNumber;
    double initWater = 0.0;
    double initLight = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantadd);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();


        inputPlantName = findViewById(R.id.plantAddNameInput);
        outPlantName = inputPlantName.getText().toString();
        //inputSpeciesName = findViewById(R.id.speciesAddInput);
        String id = inputSpeciesName.getText().toString();
        outSpeciesId = Integer.parseInt(id);
        inputSensorNumber = findViewById(R.id.sensorSerialInput);
        outSensorNumber = inputSensorNumber.getText().toString();

        newPlant = new Plant(outSensorNumber, outPlantName, outSpeciesId, initWater, initLight);

        Button submitNewPlantBtn = findViewById(R.id.newPlantButton);
        submitNewPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptAddingPlant(storedToken, newPlant);
                Snackbar.make(v, "New plant added", Snackbar.LENGTH_LONG).
                        setAction("Action", null).show();

                startActivity(new Intent(PlantAddActivity.this, PlantsActivity.class));
            }
        });

        FloatingActionButton homeAccFab = findViewById(R.id.plantAdd_homeFab);
        homeAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlantAddActivity.this, DashboardActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    public void attemptAddingPlant(String inToken, Plant inPlant){
        communicator.plantAddPost(inToken, inPlant, new Callback<okhttp3.Response>(){
            @Override
            public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Plant added", Toast.LENGTH_LONG).show();
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
