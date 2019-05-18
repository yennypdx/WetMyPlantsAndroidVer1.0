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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.restAdapter.Communicator;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantAddActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;

    EditText inputPlantName, inputSensorNumber;
    String outPlantName, outSensorNumber;
    Spinner inputPlantSpecies;
    Plant newPlant;
    int outSpeciesId;
    double initWater = 0.00;
    double initLight = 0.00;

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
        inputSensorNumber = findViewById(R.id.sensorSerialInput);
        inputPlantSpecies = findViewById(R.id.species_spinner);

        Button submitNewPlantBtn = findViewById(R.id.newPlantButton);
        submitNewPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outPlantName = inputPlantName.getText().toString();
                outSensorNumber = inputSensorNumber.getText().toString();
                String speciesTempVal = inputPlantSpecies.getSelectedItem().toString();
                switch (speciesTempVal){
                    case "Click here":
                        outSpeciesId = 701;
                        break;
                    case "Species Type One":
                        outSpeciesId = 701;
                        break;
                    case "Species Type Two":
                        outSpeciesId = 702;
                        break;
                    case "Species Type Three":
                        outSpeciesId = 703;
                        break;
                    default:
                        outSpeciesId = 701;
                        break;
                }

                newPlant = new Plant(outSensorNumber, outPlantName, outSpeciesId, initWater, initLight);
                attemptAddingPlant(storedToken, userEmail, newPlant);

                Intent intent = new Intent(PlantAddActivity.this, PlantsActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
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

    public void attemptAddingPlant(String inToken, final String userEmail, Plant inPlant){
        communicator.plantAddPost(inToken, inPlant, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
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
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
