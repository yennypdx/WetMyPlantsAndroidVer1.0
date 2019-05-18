package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.R;
import com.android.wetmyplants.restAdapter.Communicator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantEditActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;

    EditText inputName, inputPlantId;
    String outNewPlantName, outNewPlantId;
    int outSpeciesId;
    Double outCurrWater, outCurrLight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantedit);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        Intent getExtras = getIntent();
        final String userEmail = getExtras.getStringExtra("userEmail");
        final String plantName = getExtras.getStringExtra("plantName");
        final String sensorId = getExtras.getStringExtra("sensorId");
        UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();
        Plant thePlant = database.getPlant(plantName);
        outCurrWater = thePlant.getCurrentWater();
        outCurrLight = thePlant.getCurrentLight();
        outSpeciesId = thePlant.getSpeciesId();

        inputName = findViewById(R.id.plantEditNameInput);
        inputName.setText(plantName);
        inputName.setEnabled(false);
        inputPlantId = findViewById(R.id.plantEditIdInput);
        inputPlantId.setText(sensorId);
        inputPlantId.setEnabled(false);

        ImageView editPlantNameBtn = findViewById(R.id.editPlantIdBtn);
        editPlantNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName.setEnabled(true);
                inputPlantId.setEnabled(false);
            }
        });

        ImageView editSensorIdBtn = findViewById(R.id.editSensorIdBtn);
        editSensorIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPlantId.setEnabled(true);
                inputName.setEnabled(false);
            }
        });

        final Button updateBtn = findViewById(R.id.update_plant_info_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outNewPlantName = inputName.getText().toString();
                outNewPlantId = inputPlantId.getText().toString();

                Plant updatedPlant = new Plant(outNewPlantId, outNewPlantName, outSpeciesId,
                        outCurrWater, outCurrLight);
                attemptUpdatePlant(storedToken, updatedPlant, outNewPlantName);

                Intent intent = new Intent(
                        PlantEditActivity.this, PlantDetailActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("plantName", plantName);
                startActivity(intent);
            }
        });

        FloatingActionButton homePlantEditFab = findViewById(R.id.plantEdit_homeFab);
        homePlantEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlantEditActivity.this, DashboardActivity.class);
                intent2.putExtra("userEmail", userEmail);
                startActivity(intent2);
            }
        });
    }

    public void attemptUpdatePlant(String inToken, Plant inPlant, String sensorId){
        communicator.plantUpdatePut(inToken, inPlant, sensorId, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Plant updated", Toast.LENGTH_LONG).show();
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
